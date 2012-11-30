package org.smarthome.gwt.coreapp.client;

import org.smarthome.gwt.coreapp.shared.DataEntity;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>DataService</code>.
 */
public interface DataServiceAsync {
    void getSensorActivityData(AsyncCallback<String> callback) throws IllegalArgumentException;
    void updateSensorActivityData(DataEntity dataentity, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
    
    
    void getSensorNewData(AsyncCallback<String> callback) throws IllegalArgumentException;
    void addSensorNewData(DataEntity dataentity, AsyncCallback<String> callback);

    
    void getFeedbackData(AsyncCallback<String> callback) throws IllegalArgumentException;
    void addFeedbackData(DataEntity dataentity, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
}
