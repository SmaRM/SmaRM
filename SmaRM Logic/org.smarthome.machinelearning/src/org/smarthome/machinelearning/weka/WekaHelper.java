package org.smarthome.machinelearning.weka;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

@SuppressWarnings("deprecation")
public class WekaHelper {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Instances createExampleInstances() {
		// Declare two numeric attributes
		Attribute attribute1 = new Attribute("firstattribute");
		Attribute attribute2 = new Attribute("secondattribute");

		// Declare a nominal attribute along with its values
		FastVector fvNominalVal = new FastVector(3);
		fvNominalVal.addElement("nominal1");
		fvNominalVal.addElement("nominal2");
		fvNominalVal.addElement("nominal3");
		Attribute attribute3 = new Attribute("aNominal", fvNominalVal);

		// Declare the class attribute along with its values
		FastVector fvClassVal = new FastVector(2);
		fvClassVal.addElement("class1");
		fvClassVal.addElement("class2");
		Attribute classAttribute = new Attribute("classLabel", fvClassVal);

		// Declare the feature vector
		FastVector fvWekaAttributes = new FastVector(4);
		fvWekaAttributes.addElement(attribute1);
		fvWekaAttributes.addElement(attribute2);
		fvWekaAttributes.addElement(attribute3);
		fvWekaAttributes.addElement(classAttribute);

		// Create an empty training set
		Instances exampleInstance = new Instances("exampleInstances", fvWekaAttributes, 10);

		// Set class index
		exampleInstance.setClass(classAttribute);

		return exampleInstance;
	}
}
