package org.smarthome.machinelearning.weka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.smarthome.machinelearning.utils.ServiceUtils;
import org.smarthome.shared.ServiceUrl;
import org.smarthome.shared.data.FeedbackDAO;
import org.smarthome.shared.datamodel.FeedbackEntity;
import org.smarthome.shared.utils.StringUtils;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;

public class NBActivityRecognizer {

	private static Classifier classifier = null;
	private static Attribute classAttribute = null;

	// Feedback data access object
	private static final FeedbackDAO feedbackDao = FeedbackDAO.dao();

	// Feedback threshold: {start, end, priority}
	private static final double[][] FEEDBACK_THRESHOLDS = { { 0.01, 0.02, 2 }, { 0.7, 0.84, 3 }, { 0.91, 0.99, 4 } };

	private static final int NO_FEEDBACK = -1;

	public void buildClassifier(Instances trainingData) throws Exception {
		// Create Naive Bayes classifier
		NaiveBayes nbClassifier = new NaiveBayes();

		// Enable supervised discretization (see Weka API documentation)
		nbClassifier.setUseSupervisedDiscretization(true);

		// Keep class attribute for identifying predicted classifications
		classAttribute = (Attribute) trainingData.classAttribute().copy();

		// Build classifier (train hypothesis model)
		nbClassifier.buildClassifier(trainingData);
		classifier = nbClassifier;
	}

	/**
	 * Get classification value from class attribute
	 * 
	 * @param valIndex
	 *            index of classification value
	 * @return if exists, returns classification value, otherwise returns 'UNKNOWN'
	 */
	public String getClassificationValue(int valIndex) {
		String classValue = "UNKNOWN";
		try {
			classValue = classAttribute.value(valIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classValue;
	}

	public void setAttributeFilter(String rangeList) {
		Remove remove = new Remove();
		remove.setAttributeIndices(rangeList);

		FilteredClassifier filteredClassifier = new FilteredClassifier();
		filteredClassifier.setFilter(remove);
		filteredClassifier.setClassifier(classifier);

		classifier = filteredClassifier;
	}

	public List<String> classifyInstancesWithEvaluation(Instances testData) throws Exception {
		Evaluation evaluation = new Evaluation(testData);
		List<String> predictedClassValues = new ArrayList<String>();

		// Build classification model
		for (int i = 0; i < testData.numInstances(); i++) {
			Instance instance = testData.instance(i);
			try {
				evaluation.evaluateModelOnceAndRecordPrediction(classifier, instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Parse classification model
		for (Object object : evaluation.predictions()) {
			NominalPrediction prediction = (NominalPrediction) object;
			if (prediction != null) {
				double predicted = prediction.predicted();
				String predictedClassValue = getClassificationValue((int) predicted);
				predictedClassValues.add(predictedClassValue);
			}
		}

		return predictedClassValues;
	}

	public String[] classifyInstances(Instances testData) throws Exception {
		Instance instance = null;
		List<String> predictedClassValues = new ArrayList<String>();

		String predictedClassValue;
		for (int i = 0; i < testData.size(); i++) {
			instance = testData.get(i);
			predictedClassValue = classifyInstance(instance);
			predictedClassValues.add(predictedClassValue);
		}

		return predictedClassValues.toArray(new String[0]);
	}

	/**
	 * Classifies a given instance with the implemented classifier
	 * 
	 * @param testInstance
	 *            Instance to classify
	 * @return Returns predicted classification value
	 * @throws Exception
	 *             Throws exception if classifiation is not possible
	 */
	public String classifyInstance(Instance testInstance) throws Exception {
		String predictedClassValue = null;
		if (classifier != null) {

			// Classify instance
			int predictedValueIndex = (int) classifier.classifyInstance(testInstance);
			double[] classProbabilities = classifier.distributionForInstance(testInstance);
			predictedClassValue = getClassificationValue(predictedValueIndex);

			// If more than one possible classification
			double feedbackPriority = getFeedbackPriority(classProbabilities);
			if (feedbackPriority != NO_FEEDBACK) {
				FeedbackEntity feedbackEntity = createFeedbackEntity(testInstance, predictedClassValue,
						(int) feedbackPriority);
				feedbackDao.addData(feedbackEntity);
			}
		}
		return predictedClassValue;
	}

	private FeedbackEntity createFeedbackEntity(Instance instance, String classification, int priority) {
		FeedbackEntity feedbackEntity = new FeedbackEntity();

		feedbackEntity.setDateinfo(new Date());
		feedbackEntity.setTimeinfo(new Date());

		feedbackEntity.setTypeinfo("VERIFY_CLASSIFICTION");
		feedbackEntity.setClassinfo("NB");

		feedbackEntity.setReflink(instance.toString());
		feedbackEntity.setPriority(String.valueOf(priority));
		String content = "";
		try {
			String activityNameLower = classification.toLowerCase();

			// Get activity-resource mapping
			String resources = ServiceUtils.callRestService(ServiceUrl.RACE_ACTIVITYRESOURCE, activityNameLower);

			// Get activity-action mapping
			String actions = ServiceUtils.callRestService(ServiceUrl.RACE_ACTIVITYACTION, activityNameLower);

			content = String.format("activity=%s; resources=%s; actions=%s", classification, resources, actions);
			content = StringUtils.replaceNewLineCharacters(content);
		} catch (Exception e) {
			content = String.format("activity=%s;", classification);
			System.out.println(e.getMessage());
		}
		feedbackEntity.setMessage(content);
		return feedbackEntity;
	}

	private double getFeedbackPriority(double[] fDistribution) {
		if (fDistribution.length > 1) {
			// Sort in ascending order
			Arrays.sort(fDistribution);

			// Get highest and second highest probability
			double maxProb = fDistribution[fDistribution.length - 1];
			double secondMaxProb = fDistribution[fDistribution.length - 2];

			double diff = maxProb - secondMaxProb;

			// Check if difference is in a defined threshold range
			for (int i = 0; i < FEEDBACK_THRESHOLDS.length; i++) {
				if ((diff >= FEEDBACK_THRESHOLDS[i][0]) && (diff <= FEEDBACK_THRESHOLDS[i][1])) {
					return FEEDBACK_THRESHOLDS[i][2];
				}
			}
		}
		return NO_FEEDBACK;
	}
}
