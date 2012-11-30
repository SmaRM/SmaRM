package org.smarthome.knowledge.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.smarthome.knowledge.ace.AceReasonerParam.Mode;
import org.smarthome.knowledge.ace.AceSoapHelper;
import org.smarthome.knowledge.utils.ServiceUtils;
import org.smarthome.shared.utils.FileIO;
import org.smarthome.shared.utils.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/race")
public class AceReasonerService {

	private static final String RACE_WS_URL = "http://attempto.ifi.uzh.ch/ws/race/racews.perl";
	
	private static final String DEFAULT_KB_RELPATH = "/WEB-INF/kb/AceOntology.txt";
	
	private static final String ACE_RESOURCE_QUERY = "What is a resource that is allocated by p:%s?";
	private static final String ACE_ACTION_QUERY = "What is an action that is caused by p:%s?";
	
	private static String defaultKbPath;
	
	/** Init the service/servlet */
	public AceReasonerService(@Context ServletContext context) {
		defaultKbPath = context.getRealPath(DEFAULT_KB_RELPATH);
    }
	

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/query")
	public Response answerAceQuery(@FormParam("axioms") String axioms, @FormParam("query") String query) {
		Client client = Client.create();
		WebResource webResource = client.resource(RACE_WS_URL);
		
		query = StringUtils.removeQuotes(query);
		query = StringUtils.replaceNewLineCharacters(query, " ");
		
		axioms = StringUtils.removeQuotes(axioms);
		axioms = StringUtils.replaceNewLineCharacters(axioms, " ");
		
		String aceRulesSoapRequest = AceSoapHelper.buildRaceSoapRequest(axioms, query, Mode.ANSWER_QUERY.param());
		
		ClientResponse clientResponse = webResource.type(MediaType.TEXT_XML).post(ClientResponse.class, aceRulesSoapRequest);
		
		String answerSet = null;
		if (clientResponse.getStatus() != 200) {
			answerSet = "Failed. (HTTP error " + clientResponse.getStatus() + ")";
		} else {
			String soapResponse = clientResponse.getEntity(String.class);
			String answerSet1 = AceSoapHelper.getRaceUsedAxioms(soapResponse);
			String answerSet2 = AceSoapHelper.getRaceSubstitutions(answerSet1);
			answerSet = answerSet1;
			answerSet = answerSet2;
		}
		
		return Response.ok(answerSet).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/ontology/view")
	public Response getAceOntology() {
		// Read ace ontology from file system
		ResponseBuilder response;
		try {
			String aceOntology = FileIO.readFileContent(defaultKbPath);
			aceOntology = StringUtils.removeQuotes(aceOntology);
			aceOntology = StringUtils.replaceNewLineCharacters(aceOntology, " ");
			response = ServiceUtils.buildOkResponse(aceOntology);
		} catch (IOException e) {
			response = ServiceUtils.buildExceptionResponse(e);
		}

		return response.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/ontology/update")
	public Response setAceOntology(@FormParam("aceontology") String aceontology) {
		// Read ace ontology from file system
		ResponseBuilder response;
		try {
			FileIO.writeFileContent(defaultKbPath, aceontology);
			response = ServiceUtils.buildOkResponse("Ontology updated successfully.");
		} catch (IOException e) {
			response = ServiceUtils.buildExceptionResponse(e);
		}

		return response.build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/query/resource/{activityname}")
	public Response getActivityResourceMapping(@PathParam("activityname") String activityname) {
		String aceOntology = getAceOntology().getEntity().toString();
		
		String query = String.format(ACE_RESOURCE_QUERY, activityname);
		
		String resources = answerAceQuery(aceOntology, query).getEntity().toString();
		resources = formatQueryAnswerList(resources);
		
		return Response.ok(resources).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/query/action/{activityname}")
	public Response getActivityActionMapping(@PathParam("activityname") String activityname) {
		String aceOntology = getAceOntology().getEntity().toString();
		
		String query = String.format(ACE_ACTION_QUERY, activityname);
		
		String actions = answerAceQuery(aceOntology, query).getEntity().toString();
		actions = formatQueryAnswerList(actions);
		
		return Response.ok(actions).build();
	}
	

	private String formatQueryAnswerList(String answertext) {
		String result =	answertext.replaceAll("what = ", "");
		result =  StringUtils.replaceNewLineCharacters(result, ", ");
		result = result.replaceAll(", ,", ", ");
		result = result.replaceAll("  ", " ").trim();
		if (result.endsWith(",")) result = result.substring(0, result.length() - 1);
		
		return result;
	}
}
