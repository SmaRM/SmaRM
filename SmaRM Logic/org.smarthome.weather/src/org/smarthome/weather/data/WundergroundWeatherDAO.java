package org.smarthome.weather.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.smarthome.shared.utils.HttpUtils;
import org.smarthome.shared.utils.XmlUtils;
import org.smarthome.shared.weather.UnitSystem;
import org.smarthome.shared.weather.WeatherInformation;
import org.smarthome.weather.parser.WundergroundForecastParser;
import org.smarthome.weather.parser.WundergroundHistoryParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public enum WundergroundWeatherDAO {
	instance;

	private static final String API_BASEURL = "http://api.wunderground.com/api";
	private static final String API_KEY = "dabe57bc8d3b48b6"; //"9b7fe48a3627ee20";

	private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd HH");

	/**
	 * Read historical weather data via Wundergrund REST API (remote web service)
	 * 
	 * @param country
	 *            Country in ISO 3166 format (i.e. DE for Germany, US for United States)
	 * @param state
	 *            State (left part of ISO 3166-2, i.e. BW for Baden-Wuerttemberg, WA for Washington)
	 * @param city
	 *            Name of the city/town (e.g. Furtwangen or Pullman)
	 * @param date
	 *            Date in format 'YYYYMMDD'
	 * @param hour
	 *            Hour of day in 24h format
	 * @return Historical weather information for given location and date parameters
	 * @throws Exception
	 * 
	 */
	public WeatherInformation getWeatherHistory(String country, String state, String city, String date, String hour)
			throws Exception {

		String serviceCall = String.format("%s/%s/history_%s/q/%s/%s/%s.xml", API_BASEURL, API_KEY, date, country,
				state, city);

		Element docRoot = callWundergroundService(serviceCall);

		Element history = XmlUtils.getFirstChildElement(docRoot, "history");
		Element observations = XmlUtils.getFirstChildElement(history, "observations");

		Element observation = WundergroundHistoryParser.findObeservationByHour(observations, hour);

		WeatherInformation weatherData = null;
		if (observation != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd HH");
			weatherData = new WeatherInformation();
			weatherData.setUnitSystem(UnitSystem.SI);
			weatherData.setDate(dateformat.parse(date + " " + hour));
			weatherData.setCity(city);

			WundergroundHistoryParser.parseWeatherData(observation, weatherData);
		}

		return weatherData;
	}

	/**
	 * Read weather forecast data via Wundergrund REST API (remote web service)
	 * 
	 * @param country
	 *            Country in ISO 3166 format (i.e. DE for Germany, US for United States)
	 * @param state
	 *            State (left part of ISO 3166-2, i.e. BW for Baden-Wuerttemberg, WA for Washington)
	 * @param city
	 *            Name of the city/town (e.g. Furtwangen or Pullman)
	 * @param date
	 *            Date in format 'YYYYMMDD'
	 * @param hour
	 *            Hour of day in 24h format
	 * @return Weather forecast information for given location and date parameters
	 * @throws Exception
	 * 
	 */
	public WeatherInformation getWeatherForecast(String country, String state, String city, String date, String hour)
			throws Exception {

		String serviceCall = String.format("%s/%s/hourly10day/q/%s/%s/%s.xml", API_BASEURL, API_KEY, date, country,
				state, city);

		Element docRoot = callWundergroundService(serviceCall);

		Element hourlyForecast = XmlUtils.getFirstChildElement(docRoot, "hourly_forecast");

		Date datetime = dateformat.parse(date + " " + hour);
		Element forecast = WundergroundForecastParser.findForecastByDateTime(hourlyForecast, date, hour);

		WeatherInformation weatherData = null;
		if (forecast != null) {
			weatherData = new WeatherInformation();
			weatherData.setUnitSystem(UnitSystem.SI);
			weatherData.setDate(datetime);
			weatherData.setCity(city);
			
			WundergroundForecastParser.parseWeatherData(forecast, weatherData);
		}

		return weatherData;
	}

	private Element callWundergroundService(String serviceCall) throws SAXException, IOException,
			ParserConfigurationException {
		
		InputStreamReader inputStreamReader = HttpUtils.openUrl(serviceCall);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		InputSource inputSource = new InputSource(reader);
		inputSource.setEncoding(inputStreamReader.getEncoding());

		// Parse XML document into a DOM
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		// Parse and normalize XML document
		Document document = docBuilder.parse(inputSource);
		document.getDocumentElement().normalize();

		// Select weather data (XML) tree root
		Element docRoot = document.getDocumentElement();
		return docRoot;
	}
}
