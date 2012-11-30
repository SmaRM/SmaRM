package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>OntologyService</code>.
 */
public interface OntologyServiceAsync {
    void getOwlOntolology(AsyncCallback<String> callback) throws IllegalArgumentException;
    void translateOwlXmlToAce(String owlXml, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getRaceOntology(AsyncCallback<String> callback) throws IllegalArgumentException;
    void updateRaceOntology(String newOntology, AsyncCallback<Void> callback) throws IllegalArgumentException;
    void getActivityActionMapping(String activityName, AsyncCallback<String> callback) throws IllegalArgumentException;
    void getActivityResourceMapping(String activityName, AsyncCallback<String> callback) throws IllegalArgumentException;
    void answerRaceQuery(String axioms, String query, AsyncCallback<String> callback) throws IllegalArgumentException;
}
