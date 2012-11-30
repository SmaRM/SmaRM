package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>LearningService</code>.
 */
public interface LearningServiceAsync {

    void trainAndClassifyNaiveBayes(AsyncCallback<String> callback);

    void trainAndClassifyAR(AsyncCallback<String> callback);

}
