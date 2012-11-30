package org.smarthome.weather.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.smarthome.shared.Constants;
import org.smarthome.shared.data.SensorActivityDAO;
import org.smarthome.shared.data.SensorNewDAO;
import org.smarthome.shared.datamodel.SensorActivityEntity;
import org.smarthome.shared.datamodel.SensorNewEntity;
import org.smarthome.shared.weather.WeatherInformation;
import org.smarthome.weather.data.WundergroundWeatherDAO;
import org.smarthome.weather.utils.ServiceUtils;

@Path("/annotate")
public class AnnotationService {

	private static final HashMap<String, WeatherInformation> weatherHistoryMap = new HashMap<String, WeatherInformation>();
	private static final HashMap<String, WeatherInformation> weatherForecastMap = new HashMap<String, WeatherInformation>();
	
	private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH");
		
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/sensoractivity")
	public Response annotateSensorActivityData() {
		SensorActivityDAO dao = SensorActivityDAO.dao();
		List<SensorActivityEntity> entities = dao.getData();
		
		Date now = new Date();
			
		for (SensorActivityEntity entity : entities) {
			
			if (entity.getOutsidetemperature() != null && entity.getOutsidetemperature().length() >0)
				continue;
					
			// check if entity's date is earlier than now
			boolean useHistory = now.compareTo(entity.getDateinfo()) > 0;
			
			String date = dateformat.format(entity.getDateinfo());
			String hour = timeformat.format(entity.getTimeinfo());
			
			try {
				WeatherInformation weatherInfo;
				if (useHistory)
					weatherInfo = getWeatherHistory(date, hour);
				else
					weatherInfo = getWeatherForecast(date, hour);
				
				String condition = weatherInfo.getConditition();
				String temperature = weatherInfo.getTemperature();
				String tempFeeling = getTemperatureFeeling(temperature);
				
				entity.setWeathercondition(condition);
				entity.setOutsidetemperature(tempFeeling);
				
				dao.updateData(entity);
			} catch (Exception e) {

			}
		}
		return ServiceUtils.buildOkResponse(entities).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/sensornew")
	public Response annotateSensorNewData() {
		SensorNewDAO dao = SensorNewDAO.dao();
		List<SensorNewEntity> entities = dao.getData();
		
		Date now = new Date();			
		for (SensorNewEntity entity : entities) {
						
			// skip, if already annotated
			if (entity.getOutsidetemperature() != null && entity.getOutsidetemperature().length() >0)
				continue;
			
			// check if entity's date is earlier than now
			boolean useHistory = now.compareTo(entity.getDateinfo()) > 0;
			
			String date = dateformat.format(entity.getDateinfo());
			String hour = timeformat.format(entity.getTimeinfo());
			try {
				WeatherInformation weatherInfo;
				if (useHistory)
					weatherInfo = getWeatherHistory(date, hour);
				else
					weatherInfo = getWeatherForecast(date, hour);
				
				String condition = weatherInfo.getConditition();
				String temperature = weatherInfo.getTemperature();
				String tempFeeling = getTemperatureFeeling(temperature);
				
				entity.setWeathercondition(condition);
				entity.setOutsidetemperature(tempFeeling);
				
				dao.updateData(entity);
			} catch (Exception e) {
				System.out.println("error annotating sensor entity (id=" + entity.getId() + ")" + Constants.NEWLINE);
				System.out.println(e.getMessage());
			}
		}
		return ServiceUtils.buildOkResponse(entities).build();
	}
	
	private WeatherInformation getWeatherHistory(String date, String hour) {
		WeatherInformation weatherHistory = weatherHistoryMap.get(date+hour);
		
		if (weatherHistory == null) {
			try {
				WundergroundWeatherDAO weatherDao = WundergroundWeatherDAO.instance;
				weatherHistory = weatherDao.getWeatherHistory("US", "WA", "Pullman", date, hour);
				
				// If not found try previous hour
				if (weatherHistory == null) {
					String previousHour = String.valueOf(Integer.parseInt(hour) - 1);
					if (previousHour.length() < 2) {
						previousHour = "0" + previousHour;
					}
					weatherHistory = weatherDao.getWeatherHistory("US", "WA", "Pullman", date, previousHour);
				}
					
				if (weatherHistory == null) {
					weatherHistory = new WeatherInformation();
					weatherHistory.setConditition("unknown");
					weatherHistory.setTemperature("999");
				}
				
				weatherHistoryMap.put(date+hour, weatherHistory);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return weatherHistory;
	}
	
	private WeatherInformation getWeatherForecast(String date, String hour) {
		WeatherInformation weatherForecast = weatherForecastMap.get(date+hour);
		
		if (weatherForecast == null) {
			try {
				WundergroundWeatherDAO weatherDao = WundergroundWeatherDAO.instance;
				weatherForecast = weatherDao.getWeatherForecast("US", "WA", "Pullman", date, hour);
				
				// If not found try following hour
				if (weatherForecast == null) {
					String previousHour = String.valueOf(Integer.parseInt(hour) + 1);
					if (previousHour.length() < 2) {
						previousHour = "0" + previousHour;
					}
					weatherForecast = weatherDao.getWeatherHistory("US", "WA", "Pullman", date, previousHour);
				}
					
				if (weatherForecast == null) {
					weatherForecast = new WeatherInformation();
					weatherForecast.setConditition("unknown");
					weatherForecast.setTemperature("999");
				}
				
				weatherForecastMap.put(date+hour, weatherForecast);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return weatherForecast;
	}
	
	private String getTemperatureFeeling(String temperatureValue) {
		double tempValue = Double.parseDouble(temperatureValue);
		
		String tempFeeling = "undefined";
		if (tempValue < -5) tempFeeling = "freezing";
		else if (tempValue < 10) tempFeeling = "cold";
		else if (tempValue < 18) tempFeeling = "brisk";
		else if (tempValue < 25) tempFeeling = "normal";
		else if (tempValue < 39) tempFeeling = "warm";
		else if (tempValue > 100 || tempValue < -100) tempFeeling = "undefined";
		else tempFeeling = "hot";
		
		return tempFeeling;
	}
}
