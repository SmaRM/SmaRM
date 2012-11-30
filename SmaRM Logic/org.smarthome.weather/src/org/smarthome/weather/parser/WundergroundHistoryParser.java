package org.smarthome.weather.parser;

import org.smarthome.shared.utils.XmlUtils;
import org.smarthome.shared.weather.WeatherInformation;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WundergroundHistoryParser {
	
	public static void parseWeatherData(Element observation, WeatherInformation weatherData) {
    	parseTemperature(observation, weatherData);
    	parseHumidity(observation, weatherData);
    	parseWindspeed(observation, weatherData);
    	parseCondition(observation, weatherData);
	}
	
	public static Element findObeservationByHour(Element observations, String hour) {
		NodeList nodeList = observations.getElementsByTagName("observation");
		
		Element result = null;
		
		for(int i=0; i<nodeList.getLength(); i++){
			  Element observation = (Element) nodeList.item(i);
			  Element observationDate = XmlUtils.getFirstChildElement(observation, "date");
			  Element observationHour = XmlUtils.getFirstChildElement(observationDate, "hour");
			  
			  if (observationHour != null && observationHour.getFirstChild() != null) {
				  String currentHour = observationHour.getFirstChild().getTextContent();
				  if (Integer.parseInt(currentHour) == Integer.parseInt(hour)) {
					  result = observation;
					  break;
				  }
			  }
		}
		
		return result;
	}

    private static void parseTemperature(Element observation, WeatherInformation weatherData) {
        Element tempm = XmlUtils.getFirstChildElement(observation, "tempm");
        
        String tempmValue = tempm.getFirstChild().getTextContent();
        
        double celsius =  Double.parseDouble(tempmValue);
        String temperature = String.valueOf(celsius);
        weatherData.setTemperature(temperature);
    }
    
    private static void parseHumidity(Element observation, WeatherInformation weatherData) {
        Element hum = XmlUtils.getFirstChildElement(observation, "hum");
        
        String humValue = hum.getFirstChild().getTextContent();
       
        weatherData.setHumidity(humValue);
    }
    
    private static void parseWindspeed(Element observation, WeatherInformation weatherData) {
        Element wspdm = XmlUtils.getFirstChildElement(observation, "wspdm");
        
        String wspdmValue = wspdm.getFirstChild().getTextContent();
        
        double kmh = Double.parseDouble(wspdmValue);
        String windSpeed = String.valueOf(kmh);
        weatherData.setWindSpeed(windSpeed);
    }
    
    
    private static void parseCondition(Element observation, WeatherInformation weatherData) {
        Element tempi = XmlUtils.getFirstChildElement(observation, "conds");
        
        String conditition = tempi.getFirstChild().getTextContent();
        weatherData.setConditition(conditition);
    }
}
