package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("configuration")
public interface ConfigurationService extends RemoteService {
    String getDatabaseConfiguration() throws IllegalArgumentException;
    String setDatabaseConfiguration(String dbconfig) throws IllegalArgumentException;
}
