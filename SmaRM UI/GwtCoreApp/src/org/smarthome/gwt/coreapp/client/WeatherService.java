package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("weather")
public interface WeatherService extends RemoteService {
    String annotateSensorActivityData() throws IllegalArgumentException;
    String annotateSensorNewData() throws IllegalArgumentException;
}
