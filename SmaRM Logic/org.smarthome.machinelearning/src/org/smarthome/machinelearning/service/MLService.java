package org.smarthome.machinelearning.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.smarthome.machinelearning.dao.WekaDbDAO;
import org.smarthome.machinelearning.utils.ServiceUtils;
import org.smarthome.machinelearning.weka.NBActivityRecognizer;

import weka.core.Instances;

@Path("/naivebayes")
public class MLService {

	private static NBActivityRecognizer nbActivityRecognizer = new NBActivityRecognizer();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/train")
	public Response buildNaiveBayesClassifier() {
		ResponseBuilder response;
		try {
			Instances trainingData = WekaDbDAO.getTrainingInstances();
			nbActivityRecognizer.buildClassifier(trainingData);
			response = ServiceUtils.buildOkResponse("NB trained successfully.");

		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e);
		}
		return response.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/classify/sensornew")
	public Response classifySensorNewData() {
		ResponseBuilder response;
		try {
			Instances testData = WekaDbDAO.getTestInstances();
			nbActivityRecognizer.classifyInstances(testData);

			response = ServiceUtils.buildOkResponse("NB trained successfully.");

		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e);
		}
		return response.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/trainandclassify")
	public Response trainAndClassify() {
		ResponseBuilder response;
		try {
			buildNaiveBayesClassifier();
			classifySensorNewData();
			response = ServiceUtils.buildOkResponse("Trained and classified.");

		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e);
		}
		return response.build();
	}
}
