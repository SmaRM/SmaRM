package org.smarthome.knowledge.service;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.smarthome.knowledge.ace.AceParserParam;
import org.smarthome.knowledge.ace.AceParserParam.Input;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Path("/ape")
public class AceParserService {

	// OWL Verbalizer web service
	private static final String APE_WS_URL = "http://attempto.ifi.uzh.ch/ws/ape/apews.perl";
	
	@Context
	ServletContext context;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getOwlFromAce(@FormParam("lexicon") String aceLexicon, @FormParam("text") String aceText) {

		Client client = Client.create();
		WebResource webResource = client.resource(APE_WS_URL);

		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		
		// Set text and user-defined lexicon
		queryParams.add(Input.TEXT.param(), aceText);
		if (aceLexicon != null && aceLexicon.length() > 0) {
			queryParams.add(Input.ULEXTEXT.param(), aceLexicon);
		}
		
		// Set format parameters
		queryParams.add("solo", AceParserParam.OutputFormat.OWLXML.soloParam());
		
		ClientResponse clientResponse = webResource.queryParams(queryParams).type(MediaType.TEXT_PLAIN).get(ClientResponse.class);

		String owlXmlResult;
		if (clientResponse.getStatus() != 200) {
			owlXmlResult = "Failed. (HTTP error " + clientResponse.getStatus() + ")";
		} else {
			owlXmlResult = clientResponse.getEntity(String.class);
		}
		
		return Response.ok(owlXmlResult).build();
	}
}
