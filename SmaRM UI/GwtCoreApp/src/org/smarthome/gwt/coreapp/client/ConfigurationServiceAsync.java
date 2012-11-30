package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ConfigurationService</code>.
 */
public interface ConfigurationServiceAsync {
    void getDatabaseConfiguration(AsyncCallback<String> callback);

    void setDatabaseConfiguration(String dbconfig, AsyncCallback<String> callback);
}
