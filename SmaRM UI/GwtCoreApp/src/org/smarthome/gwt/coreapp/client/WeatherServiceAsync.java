package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>WeatherService</code>.
 */
public interface WeatherServiceAsync {
    void annotateSensorActivityData(AsyncCallback<String> callback);

    void annotateSensorNewData(AsyncCallback<String> callback);
}
