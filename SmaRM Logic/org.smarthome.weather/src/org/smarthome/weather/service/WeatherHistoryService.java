package org.smarthome.weather.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.smarthome.shared.weather.WeatherInformation;
import org.smarthome.weather.data.WundergroundWeatherDAO;
import org.smarthome.weather.utils.ServiceUtils;

@Path("/history")
public class WeatherHistoryService {

	// RESTful Services using path parameters (@PathParam)
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("{yyyymmdd}/{hour}/{country}/{state}/{location}.{format}")
	public Response getWeatherData(@PathParam("yyyymmdd") String date,
								   @PathParam("hour") String hour,
								   @PathParam("country") String country,
			                       @PathParam("state") String state,
								   @PathParam("location") String city, 
			                       @PathParam("format") String format) {
		ResponseBuilder response = null;

		try {
			WundergroundWeatherDAO weatherDao = WundergroundWeatherDAO.instance;
			
			WeatherInformation weatherHistory = 
					weatherDao.getWeatherHistory(country, state, city, date, hour);
			
			response = ServiceUtils.buildOkResponse(weatherHistory, format);
		} catch (Exception e) {
			response = ServiceUtils.buildExceptionResponse(e, format);
		}
		return response.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getWeatherDataPost(@FormParam("yyyymmdd") String date,
								   @FormParam("hour") String hour,
								   @FormParam("country") String country,
								   @FormParam("state") String state,
								   @FormParam("location") String city, 
								   @FormParam("format") String format) {
		return getWeatherData(date, hour, country, state, city, format);
	}
}
