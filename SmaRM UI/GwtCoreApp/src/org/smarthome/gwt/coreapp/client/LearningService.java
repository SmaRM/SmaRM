package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("learning")
public interface LearningService extends RemoteService {
    String trainAndClassifyNaiveBayes() throws IllegalArgumentException;
    String trainAndClassifyAR() throws IllegalArgumentException;
}
