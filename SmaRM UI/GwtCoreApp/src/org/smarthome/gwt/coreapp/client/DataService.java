package org.smarthome.gwt.coreapp.client;

import org.smarthome.gwt.coreapp.shared.DataEntity;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("data")
public interface DataService extends RemoteService {
    String getSensorActivityData() throws IllegalArgumentException;
    boolean updateSensorActivityData(DataEntity dataentity) throws IllegalArgumentException;
    
    String getSensorNewData() throws IllegalArgumentException;
    String addSensorNewData(DataEntity dataentity) throws IllegalArgumentException;
    
    String getFeedbackData() throws IllegalArgumentException;
    boolean addFeedbackData(DataEntity dataentity) throws IllegalArgumentException;
}
