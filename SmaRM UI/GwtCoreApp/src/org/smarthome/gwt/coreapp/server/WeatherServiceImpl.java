package org.smarthome.gwt.coreapp.server;

import org.smarthome.gwt.coreapp.client.WeatherService;
import org.smarthome.gwt.coreapp.server.utils.HttpHelper;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class WeatherServiceImpl extends RemoteServiceServlet implements WeatherService {
    @Override
    public String annotateSensorActivityData() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.WEATHER_ANNOTATE_SENSOR_ACTIVITY);
        return result;
    }

    @Override
    public String annotateSensorNewData() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.WEATHER_ANNOTATE_SENSOR_NEW);
        return result;
    }

}
