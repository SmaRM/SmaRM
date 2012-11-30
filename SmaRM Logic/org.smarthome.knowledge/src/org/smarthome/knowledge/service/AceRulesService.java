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

import org.smarthome.knowledge.ace.AceSoapHelper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/acerules")
public class AceRulesService {

	private static final String ACERULES_WS_URL = "http://attempto.ifi.uzh.ch/ws/acerules/acerulesws.perl";
	
	@Context
	ServletContext context;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getOwlFromAce(@FormParam("program") String program) {
		final int maxAnswers = 100;

		Client client = Client.create();
		WebResource webResource = client.resource(ACERULES_WS_URL);
		
		String aceRulesSoapRequest = AceSoapHelper.buildAceRulesSoapRequest(program, maxAnswers);
		
		ClientResponse clientResponse = webResource.type(MediaType.TEXT_XML).post(ClientResponse.class, aceRulesSoapRequest);
		
		String aceResult;
		if (clientResponse.getStatus() != 200) {
			aceResult = "Failed. (HTTP error " + clientResponse.getStatus() + ")";
		} else {
			String soapResponse = clientResponse.getEntity(String.class);
			aceResult = AceSoapHelper.getAceRulesAnswerText(soapResponse);
		}
		
		return Response.ok(aceResult).build();
	}
}
