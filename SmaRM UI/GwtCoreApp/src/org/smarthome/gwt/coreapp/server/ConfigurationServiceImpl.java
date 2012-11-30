package org.smarthome.gwt.coreapp.server;

import org.smarthome.gwt.coreapp.client.ConfigurationService;
import org.smarthome.gwt.coreapp.server.utils.HttpHelper;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConfigurationServiceImpl extends RemoteServiceServlet implements ConfigurationService {

    @Override
    public String getDatabaseConfiguration() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.CONFIGURATION_DB);
        return result;
    }

    @Override
    public String setDatabaseConfiguration(String dbconfig) throws IllegalArgumentException {
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("dbconfig", dbconfig);
        
        String result = HttpHelper.postFormRequest(ServiceUrl.CONFIGURATION_DB_UPDATE, paramMap);
        return result;
    }

}
