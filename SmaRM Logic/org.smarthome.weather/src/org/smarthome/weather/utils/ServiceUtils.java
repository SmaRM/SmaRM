package org.smarthome.weather.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.smarthome.shared.json.JsonProcessor;

public class ServiceUtils {

	public static ResponseBuilder buildOkResponse(Object entity, String format) {
		ResponseBuilder response;
		if (format != null && format.endsWith("xml")) {
			response = Response.ok(entity).type(MediaType.APPLICATION_XML);
		} else {
			String json = JsonProcessor.getInstance().toJson(entity);
			response = Response.ok(json).type(MediaType.APPLICATION_JSON);
		}
		return response;
	}
	
	public static ResponseBuilder buildExceptionResponse(Exception exception, String format) {
		String message = (exception == null) ? "Unrecognized exception!" : exception.getMessage();
		return buildOkResponse(message, format);
	}
	
	public static ResponseBuilder buildOkResponse(Object entity) {
		return buildOkResponse(entity, MediaType.APPLICATION_JSON);
	}
	
	public static ResponseBuilder buildExceptionResponse(Exception exception) {
		return buildExceptionResponse(exception, MediaType.APPLICATION_JSON);
	}

}
