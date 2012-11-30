package org.smarthome.activityrecognition.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.smarthome.activityrecognition.jni.ActivityRecognizer;
import org.smarthome.shared.data.SensorActivityDAO;
import org.smarthome.shared.data.SensorNewDAO;
import org.smarthome.shared.datamodel.SensorActivityEntity;
import org.smarthome.shared.datamodel.SensorNewEntity;

@Path("/")
public class ARService {
	
	private static ActivityRecognizer activityRecognizer = null;
	private static ActivityRecognizer activityModel = null;

	private static final String AR_BASEPATH = "WEB-INF/ar";
	private static final String AR_CONFIGFILE = "ar.config";
	private static final String MODEL_CONFIGFILE = "model.config";

	private static SensorActivityDAO sensorActivityDAO = SensorActivityDAO.dao();
	private static SensorNewDAO sensorNewDao = SensorNewDAO.dao();
	
	private static String arConfig;
	private static String modelConfig;
	
    @Context
	ServletContext context;
    
	/** Init the AR service/servlet */
	public ARService(@Context ServletContext context) {
		initActivityRecognizer(context.getRealPath(AR_BASEPATH));
		System.out.println(arConfig);
    }
	
	private void initActivityRecognizer(String baseDirectory) {
    	if (activityRecognizer == null) {
			try {
				activityRecognizer = new ActivityRecognizer(baseDirectory);
				arConfig = activityRecognizer.readConfiguration(AR_CONFIGFILE);
				arConfig = String.format("Activity recognizer initialized: %1$s %n", arConfig);
			} catch (Exception e) {
				arConfig = "Unable to create activity recognizer instance!";
			}
		}
	}
	
	private void initActivityModel(String baseDirectory) {
    	if (activityModel == null) {
			try {
				activityModel = new ActivityRecognizer(baseDirectory);
				modelConfig = activityRecognizer.readConfiguration(MODEL_CONFIGFILE);
				modelConfig = String.format("Activity predictor created: %1$s %n", modelConfig);
			} catch (Exception e) {
				modelConfig = "Unable to create activity model instance!";
			}
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/train/sampledata")
	public Response trainFromFile() {
		String trainedModel = activityRecognizer.trainSVM();

		return Response.ok(trainedModel).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/train/sensoractivity")
	public Response trainFromSensorData() {
		List<SensorActivityEntity> result = sensorActivityDAO.getData();

		StringBuffer dataBuffer = new StringBuffer();
		for (SensorActivityEntity sensorActivityEntity : result) {
			dataBuffer.append(sensorActivityEntity.arSvmFormat());
		}
				
		String trainedModel = activityRecognizer.trainSVM(dataBuffer.toString());
		return Response.ok(trainedModel).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/classify/senoractivity")
	public Response classifyTestData() {
		// Read activity recognizer (model) configuration
		initActivityModel(context.getRealPath(AR_BASEPATH));
		System.out.println(modelConfig);
		
		// Get test data
		List<SensorActivityEntity> sensorData = sensorActivityDAO.getData();
		StringBuffer dataBuffer = new StringBuffer();
		for (SensorActivityEntity sensorEntity : sensorData) {
			dataBuffer.append(sensorEntity.arSvmFormat());
		}
		
		// Classify test data
		String results = activityRecognizer.testSVM(dataBuffer.toString());
		return Response.ok(results).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/classify/sensornew")
	public Response classifySensorNew() {
		// Read activity recognizer (model) configuration
		initActivityModel(context.getRealPath(AR_BASEPATH));
		System.out.println(modelConfig);
		
		// Get test data
		List<SensorNewEntity> sensorData = sensorNewDao.getData();
		StringBuffer dataBuffer = new StringBuffer();
		for (SensorNewEntity sensorEntity : sensorData) {
			dataBuffer.append(sensorEntity.arSvmFormat());
		}
		
		// Classify test data
		String results = activityRecognizer.testSVM(dataBuffer.toString());
		return Response.ok(results).build();
	}
}
