package org.smarthome.gwt.coreapp.server;

import org.smarthome.gwt.coreapp.client.OntologyService;
import org.smarthome.gwt.coreapp.server.utils.HttpHelper;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class OntologyServiceImpl extends RemoteServiceServlet implements OntologyService {

    @Override
    public String getOwlOntolology() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.DEFAULT_OWLONTOLOGY);
        return result;
    }

    @Override
    public String translateOwlXmlToAce(String owlXml) throws IllegalArgumentException {
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("xml", owlXml);
        paramMap.add("outputFormat", null);
        
        String aceText = HttpHelper.postFormRequest(ServiceUrl.OWLVERBALIZER, paramMap);

        return aceText;
    }

    @Override
    public String getRaceOntology() throws IllegalArgumentException {
        String result = HttpHelper.readFromUrl(ServiceUrl.DEFAULT_RACEONTOLOGY);
        return result;
    }

    @Override
    public void updateRaceOntology(String newOntology) throws IllegalArgumentException {
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("aceontology", newOntology);
        
        String result = HttpHelper.postFormRequest(ServiceUrl.DEFAULT_RACEONTOLOGY_UPDATE, paramMap);
        System.out.println(result);
    }

    @Override
    public String getActivityActionMapping(String activityName) throws IllegalArgumentException {
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("activityname", activityName);

        String actions = HttpHelper.callRestService(ServiceUrl.RACE_ACTIVITYACTION, activityName);
        return actions;
    }

    @Override
    public String getActivityResourceMapping(String activityName) throws IllegalArgumentException {
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("activityname", activityName);

        String resources = HttpHelper.callRestService(ServiceUrl.RACE_ACTIVITYRESOURCE, activityName);
        return resources;
    }

    @Override
    public String answerRaceQuery(String axioms, String query) throws IllegalArgumentException {
        MultivaluedMapImpl paramMap = new MultivaluedMapImpl();
        paramMap.add("axioms", axioms);
        paramMap.add("query", query);
        
        String queryAnswer = HttpHelper.postFormRequest(ServiceUrl.RACE_QUERY, paramMap);
        return queryAnswer;
    }
}