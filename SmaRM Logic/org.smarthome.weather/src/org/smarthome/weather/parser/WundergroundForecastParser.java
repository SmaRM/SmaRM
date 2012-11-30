package org.smarthome.weather.parser;

import org.smarthome.shared.utils.XmlUtils;
import org.smarthome.shared.weather.WeatherInformation;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WundergroundForecastParser {
	
	public static void parseWeatherData(Element forecast, WeatherInformation weatherData) {
    	parseTemperature(forecast, weatherData);
    	parseHumidity(forecast, weatherData);
    	parseWindspeed(forecast, weatherData);
    	parseCondition(forecast, weatherData);
	}
	
	public static Element findForecastByDateTime(Element hourlyForecast, String date, String hour) {
		NodeList nodeList = hourlyForecast.getElementsByTagName("forecast");

		Element result = null;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element forecast = (Element) nodeList.item(i);
			Element fcttime = XmlUtils.getFirstChildElement(forecast, "FCTTIME");
			
			String fhour = XmlUtils.getFirstChildElement(fcttime, "hour_padded").getFirstChild().getTextContent();
			String fday = XmlUtils.getFirstChildElement(fcttime, "mday_padded").getFirstChild().getTextContent();
			String fmonth = XmlUtils.getFirstChildElement(fcttime, "mon_padded").getFirstChild().getTextContent();
			String fyear = XmlUtils.getFirstChildElement(fcttime, "year").getFirstChild().getTextContent();
			
			String fdate = fyear + fmonth + fday;
			if (date.equals(fdate) && hour.equals(fhour)) {
				result = forecast;
				break;
			}
		}

		return result;
	}

    private static void parseTemperature(Element forecast, WeatherInformation weatherData) {
        Element temp = XmlUtils.getFirstChildElement(forecast, "temp");
        Element tempm = XmlUtils.getFirstChildElement(temp, "metric");
             
        String tempmValue = tempm.getFirstChild().getTextContent();

        double celsius =  Double.parseDouble(tempmValue);        
        String temperature = String.valueOf(celsius);
        weatherData.setTemperature(temperature);
    }
    
    private static void parseHumidity(Element forecast, WeatherInformation weatherData) {
        Element hum = XmlUtils.getFirstChildElement(forecast, "humidity");
        
        String humValue = hum.getFirstChild().getTextContent();
       
        weatherData.setHumidity(humValue);
    }
    
    private static void parseWindspeed(Element forecast, WeatherInformation weatherData) {
        Element wspd = XmlUtils.getFirstChildElement(forecast, "wspd");
        Element wspdm = XmlUtils.getFirstChildElement(wspd, "metric");
        
        String wspdmValue = wspdm.getFirstChild().getTextContent();
        
        double kmh = Double.parseDouble(wspdmValue);
        String windSpeed = String.valueOf(kmh);
        weatherData.setWindSpeed(windSpeed);
    }
    
    
    private static void parseCondition(Element forecast, WeatherInformation weatherData) {
        Element tempi = XmlUtils.getFirstChildElement(forecast, "condition");
        
        String conditition = tempi.getFirstChild().getTextContent();
        weatherData.setConditition(conditition);
    }
   	
	
}
