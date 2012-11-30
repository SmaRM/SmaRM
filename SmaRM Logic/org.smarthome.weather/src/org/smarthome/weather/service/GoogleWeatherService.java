package org.smarthome.weather.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.smarthome.shared.weather.WeatherInformation;
import org.smarthome.weather.data.GoogleWeatherDAO;
import org.smarthome.weather.utils.ServiceUtils;

@Path("/gweather")
public class GoogleWeatherService {

	// RESTful Services using path parameters (@PathParam)
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("{location}.{format}")
	public Response getWeatherData(@PathParam("location") String location, 
			                       @PathParam("format") String format) {
		ResponseBuilder response = null;

		try {
			GoogleWeatherDAO weatherDao = GoogleWeatherDAO.instance;
			WeatherInformation weatherInformation = 
					weatherDao.getWeatherInformation(location);
			
			response = ServiceUtils.buildOkResponse(weatherInformation, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}

		return response.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{location}")
	public Response getWeatherData(@PathParam("location") String location) {
		return getWeatherData(location, MediaType.APPLICATION_JSON);
	}

	// RESTful Services using query parameters (@QueryParam)

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryWeatherData(@QueryParam("query") String location, 	
									 @QueryParam("format") String format) {
		return getWeatherData(location, format);
	}

	// RESTful Services using form parameters (@FormParam)

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getWeatherDataPost(@FormParam("location") String location, 
									   @FormParam("format") String format) {
		return getWeatherData(location, format);
	}
}
