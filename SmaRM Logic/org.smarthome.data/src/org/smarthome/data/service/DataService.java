package org.smarthome.data.service;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.smarthome.data.utils.ServiceUtils;
import org.smarthome.shared.Constants;
import org.smarthome.shared.data.FeedbackDAO;
import org.smarthome.shared.data.LabeledDataDAO;
import org.smarthome.shared.data.SensorActivityDAO;
import org.smarthome.shared.data.SensorNewDAO;
import org.smarthome.shared.datamodel.FeedbackEntity;
import org.smarthome.shared.datamodel.LabeledDataEntity;
import org.smarthome.shared.datamodel.SensorActivityEntity;
import org.smarthome.shared.datamodel.SensorNewEntity;
import org.smarthome.shared.json.JsonProcessor;

@Path("/resources")
public class DataService {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);

	// Data access objects:
	private static LabeledDataDAO labeledDataDao = LabeledDataDAO.dao();
	
	private static SensorNewDAO sensorNewDao = SensorNewDAO.dao();
	private static SensorActivityDAO sensorActivityDao = SensorActivityDAO.dao();
	private static FeedbackDAO feedbackDao = FeedbackDAO.dao();

	@Context
	ServletContext context;

	@Context
	HttpServletResponse servletResponse;
	
	// RESTful Services using path parameters (@PathParam)

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/labeleddata.{format}/all")
	public Response getResourceData(@PathParam("format") String format) {
		ResponseBuilder response;
		try {
			// Get data
			List<LabeledDataEntity> dataEntries = labeledDataDao.getData();

			// Build response in JSON format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/sensoractivity.{format}/all")
	public Response getSensorActivityData(@PathParam("format") String format) {
		ResponseBuilder response;
		try {
			// Get data
			List<SensorActivityEntity> dataEntries = sensorActivityDao.getData();

			// Build response in selected format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/sensoractivity.{format}/{limit}")
	public Response getSensorActivityData(@PathParam("format") String format, @PathParam("limit") int limit) {
		ResponseBuilder response;
		try {
			// Get data
			List<SensorActivityEntity> dataEntries = sensorActivityDao.getDataWithLimit(limit);

			// Build response in selected format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/sensornew.{format}/all")
	public Response getSensorNewData(@PathParam("format") String format) {
		ResponseBuilder response;
		try {
			// Get data
			List<SensorNewEntity> dataEntries = sensorNewDao.getData();
			// Build response in selected format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/sensornew.{format}/{limit}")
	public Response getSensorNewData(@PathParam("format") String format, @PathParam("limit") int limit) {
		ResponseBuilder response;
		try {
			// Get data
			List<SensorNewEntity> dataEntries = sensorNewDao.getDataWithLimit(limit);
			// Build response in selected format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/feedback.{format}/all")
	public Response getFeedbackData(@PathParam("format") String format) {
		ResponseBuilder response;
		try {
			// Get data
			List<FeedbackEntity> dataEntries = feedbackDao.getData();

			// Build response in selected format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/feedback.{format}/{limit}")
	public Response getFeedbackData(@PathParam("format") String format, @PathParam("limit") int limit) {
		ResponseBuilder response;
		try {
			// Get data
			List<FeedbackEntity> dataEntries = feedbackDao.getDataWithLimit(limit);

			// Build response in selected format
			response = ServiceUtils.buildOkResponse(dataEntries, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	@Path("/sensornew/add/json")
	public void addNewSensorDataFromJson(@FormParam("sensornew") String sensornewJson) throws IOException {
		SensorNewEntity sensorNewEntity = JsonProcessor.getInstance().fromJson(sensornewJson, SensorNewEntity.class);
		
		persistNewSensorEntity(sensorNewEntity);
	}
	
	// RESTful Services using form parameters (@FormParam)
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	@Path("/sensornew/add")
	public void addNewSensorData(@FormParam("date") String date, 
							@FormParam("time") String time, 
							@FormParam("location") String location,
							@FormParam("sensorid") String sensorid,
							@FormParam("sensorvalue") String sensorvalue) throws IOException {

		// Create new object
		SensorNewEntity sensorNewEntity = new SensorNewEntity();

		try {
			sensorNewEntity.setDateinfo(dateFormat.parse(date));
		} catch (ParseException e) {
			sensorNewEntity.setDateinfo(new Date(0));
		}
		
		try {
			sensorNewEntity.setTimeinfo(Time.valueOf(time));
		} catch (Exception e) {
			sensorNewEntity.setTimeinfo(Time.valueOf("00:00:00"));
		}
		
		sensorNewEntity.setLocation(location);
		sensorNewEntity.setSensorid(sensorid);
		sensorNewEntity.setSensorvalue(sensorvalue);
		
		persistNewSensorEntity(sensorNewEntity);
	}
	
	private void persistNewSensorEntity(SensorNewEntity entity) {
		// Insert into database
		try {
			sensorNewDao.addData(entity);
			
			servletResponse.setStatus(HttpServletResponse.SC_CREATED);
			servletResponse.sendRedirect(context.getContextPath() + "/rest/resources/resource_created");
		} catch (Exception e) {
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	@Path("/sensornew/remove")
	public void removeNewSensorData(@FormParam("index") String index) throws IOException {

	    // Remove from database
		try {
			int id = Integer.parseInt(index);
			sensorNewDao.removeData(id);
			
			servletResponse.setStatus(HttpServletResponse.SC_OK);
			String msg = "Entity removed from database.";
			servletResponse.sendRedirect(context.getContextPath() + "/rest/resources/response/" + msg);
		} catch (Exception e) {
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public void addResource(@FormParam("userId") String userId, @FormParam("date") String date,
			@FormParam("location") String locations, @FormParam("activity") String activity,
			@FormParam("duration") String duration, @FormParam("energyConsumption") String energyConsumption,
			@FormParam("waterConsumption") String waterConsumption) throws IOException {

		// Create new object
		LabeledDataEntity labeledData = new LabeledDataEntity();
		labeledData.setUserId(userId);
		try {
			labeledData.setDateTime(new SimpleDateFormat(Constants.DATETIME_FORMAT).parse(date));
		} catch (ParseException e) {
			labeledData.setDateTime(new Date(0));
		}
		labeledData.setLocations(locations);
		labeledData.setActivity(activity);
		labeledData.setDuration(Integer.parseInt(duration));
		labeledData.setEnergyConsumption(Double.parseDouble(energyConsumption));
		labeledData.setWaterConsumption(Double.parseDouble(energyConsumption));

		// Insert into database
		try {
			labeledDataDao.addData(labeledData);
			servletResponse.setStatus(HttpServletResponse.SC_CREATED);
			String msg = "Entity added to database.";
			servletResponse.sendRedirect(context.getContextPath() + "/rest/resources/response/" + msg);
		} catch (Exception e) {
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/response/{message}")
	public Response htmlResponseDialog(@PathParam("message") String message) {
		String result = "<html><head><title>Info</title>";
		result += "<script type=\"text/javascript\">alert(\"" + message + "\");";
		result += "window.location=\"" + context.getContextPath() + "\"</script></head>";
		result += "<body></body></html>";
		return Response.ok(result, MediaType.TEXT_HTML).build();
	}

}
