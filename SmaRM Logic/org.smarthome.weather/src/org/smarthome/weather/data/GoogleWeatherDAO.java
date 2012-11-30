package org.smarthome.weather.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.smarthome.shared.Constants;
import org.smarthome.shared.utils.Convert;
import org.smarthome.shared.utils.HttpUtils;
import org.smarthome.shared.utils.XmlUtils;
import org.smarthome.shared.weather.UnitSystem;
import org.smarthome.shared.weather.WeatherInformation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public enum GoogleWeatherDAO {
	instance;
	
    // Basic Google Weather API service URL (location variable: %s)
	// Note: '&hl=en' returns English and US units 
    private final String GWEATHER_BASEURL = "http://www.google.com/ig/api?weather=%s&hl=en";

    // Date format used by weather service
    private final String GWEATHER_DATEFORMAT = "yyyy-MM-dd";
    
    public WeatherInformation getWeatherInformation(String location) throws Exception {
    	return readWeatherInformation(location);
    }
    
    private WeatherInformation readWeatherInformation(String location) throws IOException, ParserConfigurationException, ParseException, SAXException {
    	// Create service call
    	URL serviceUrl = new URL(String.format(GWEATHER_BASEURL, location));
        // Create new HTTP connection
        URLConnection urlConnection = serviceUrl.openConnection();
        // Set connection timeout [ms]
        urlConnection.setConnectTimeout(Constants.HTTP_CONNECTION_TIMEOUT_MS);
    
        // Get content type and charset
        String contentType = urlConnection.getContentType();
        String charset = HttpUtils.getCharset(contentType);
        
        // Open input stream for reading
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        InputSource inputSource = new InputSource(reader);
        inputSource.setEncoding(charset);
 
        // Parse XML document into a DOM
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        
        // Parse and normalize XML document
        Document document = docBuilder.parse(inputSource);
        document.getDocumentElement().normalize();

        // Select weather data (XML) tree root
        Element docRoot = document.getDocumentElement();
        Element weatherElement = XmlUtils.getFirstChildElement(docRoot, "weather");

        // Parse request information (city, date, etc.)
        Element forecastInformation = XmlUtils.getFirstChildElement(weatherElement, "forecast_information");

        // Parse current weather data
        Element currentConditions = XmlUtils.getFirstChildElement(weatherElement, "current_conditions");

        // Parse forecast data
        Element forecastConditions = XmlUtils.getFirstChildElement(weatherElement, "forecast_conditions");
        
        // Create result object
        WeatherInformation weatherData = new WeatherInformation();
        weatherData.setUnitSystem(UnitSystem.SI);
        parseForcastInformation(forecastInformation, weatherData);
        parseCurrentConditions(currentConditions, weatherData);
        parseForecastConditions(forecastConditions, weatherData);
        
        return weatherData;
    }

	private void parseForcastInformation(Element forecastInformation, WeatherInformation weatherData) throws ParseException {
        weatherData.setCity(getDataAttributeValue(forecastInformation, "city"));

        DateFormat dateFormat = new SimpleDateFormat(GWEATHER_DATEFORMAT);
        Date date = dateFormat.parse(getDataAttributeValue(forecastInformation, "forecast_date"));
        weatherData.setDate(date); 

        weatherData.setTime(new Date().getTime());
    }

    private void parseCurrentConditions(Element currentConditions, WeatherInformation weatherData) {
        weatherData.setConditition(getDataAttributeValue(currentConditions, "condition"));

        weatherData.setTemperature(getDataAttributeValue(currentConditions, "temp_c"));

        weatherData.setHumidity(getDataAttributeValue(currentConditions, "humidity").replaceAll("[^0-9]", ""));

        String windSpeedMph = getDataAttributeValue(currentConditions, "wind_condition").replaceAll("[^0-9]", "");
        int windSpeedKmh = (int) Convert.mphToKmh(Double.parseDouble(windSpeedMph));
        weatherData.setWindSpeed(String.valueOf(windSpeedKmh));
    }

    private void parseForecastConditions(Element forecastConditions, WeatherInformation weatherData) {
        // TODO: future usage
    }

    private String getDataAttributeValue(Element parent, String childName) {    
        Element firstChild = XmlUtils.getFirstChildElement(parent, childName);
        
        String dataValue = "";
        if (firstChild != null && firstChild.hasAttribute("data")) {
            dataValue = firstChild.getAttribute("data");
        }
        return dataValue;
    }
}
