package org.smarthome.gwt.coreapp.server;

import org.smarthome.gwt.coreapp.client.DataService;
import org.smarthome.gwt.coreapp.server.utils.HttpHelper;
import org.smarthome.gwt.coreapp.shared.DataEntity;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements DataService {
    
    @Override
    public String getSensorActivityData() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.SENSOR_ACTIVITY_DATA_FIRST30);        
        return result;
    }
    
    @Override
    public boolean updateSensorActivityData(DataEntity dataentity) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getSensorNewData() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.SENSOR_NEW_DATA_ALL);     
        return result;
    }
    
    @Override
    public String addSensorNewData(DataEntity dataentity) throws IllegalArgumentException {
        String jsonString = new Gson().toJson(dataentity);
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("sensornew", jsonString);

        String newEntity = HttpHelper.postFormRequest(ServiceUrl.SENSOR_NEW_DATA_ADD, paramMap);
        return newEntity;
    }

    @Override
    public String getFeedbackData() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.FEEDBACK_ALL);     
        return result;
    }
    
    @Override
    public boolean addFeedbackData(DataEntity dataentity) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return false;
    }
}
