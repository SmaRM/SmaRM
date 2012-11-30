package org.smarthome.gwt.coreapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("ontology")
public interface OntologyService extends RemoteService {
    String getOwlOntolology() throws IllegalArgumentException;
    String translateOwlXmlToAce(String owlXml) throws IllegalArgumentException;
    
    String getRaceOntology() throws IllegalArgumentException;
    void updateRaceOntology(String newOntology) throws IllegalArgumentException;;
    
    String getActivityActionMapping(String activityName) throws IllegalArgumentException;;
    String getActivityResourceMapping(String activityName) throws IllegalArgumentException;
    
    String answerRaceQuery(String axioms, String query) throws IllegalArgumentException;
}
