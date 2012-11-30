package org.smarthome.shared.json;

import org.smarthome.shared.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonProcessor {
	
	private static JsonProcessor instance = null;

	public static JsonProcessor getInstance() {
		if (instance == null) {
			instance = new JsonProcessor();
		}
		return instance;
	}

	private Gson serializer;

	private JsonProcessor() {
		GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(Constants.DATETIME_FORMAT);
		serializer = gsonBuilder.setPrettyPrinting().create();
	}

	public String toJson(Object src) {
		return serializer.toJson(src);
	}

	public <T> T fromJson(String json, Class<T> targetClass) {
		return serializer.fromJson(json, targetClass);
	}

	public Gson getSerializer() {
		return serializer;
	}
}
