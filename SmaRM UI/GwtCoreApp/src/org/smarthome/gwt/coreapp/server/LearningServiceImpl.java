package org.smarthome.gwt.coreapp.server;

import org.smarthome.gwt.coreapp.client.LearningService;
import org.smarthome.gwt.coreapp.server.utils.HttpHelper;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LearningServiceImpl extends RemoteServiceServlet implements LearningService {
    
    @Override
    public String trainAndClassifyNaiveBayes() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.NB_TRAINANDCLASSIFY);        
        return result;
    }

    @Override
    public String trainAndClassifyAR() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.AR_TRAIN_SENSORACTIVITY);
        result += HttpHelper.readFromUrl(ServiceUrl.AR_CLASSIFY_SENSORNEW);
        return result;
    }
}
