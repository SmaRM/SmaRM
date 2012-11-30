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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Path("/verbalize")
public class VerbalizerService {
	
	public enum OutputFormat {
		ACE,
		CSV,
		HTML
	}

	// OWL Verbalizer web service
	private static final String OWL_TO_ACE_WS_URL = "http://attempto.ifi.uzh.ch/service/owl_verbalizer/owl_to_ace";
	
	@Context
	ServletContext context;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAceFromOwlXml(@FormParam("xml") String xml, @FormParam("outputFormat") String outputFormat) {

		Client client = Client.create();
		WebResource webResource = client.resource(OWL_TO_ACE_WS_URL);

		MultivaluedMapImpl values = new MultivaluedMapImpl();
		values.add("xml", xml);
		
		if (outputFormat != null && !outputFormat.isEmpty()) {
			values.add("format", outputFormat);
		}
		
		ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, values);
		
		String aceResult;
		if (clientResponse.getStatus() != 200) {
			aceResult = "Failed. (HTTP error " + clientResponse.getStatus() + ")";
		} else {
			aceResult = clientResponse.getEntity(String.class);
		}
		
		return Response.ok(aceResult).build();
	}
}
