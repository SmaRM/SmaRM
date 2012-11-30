package org.smarthome.knowledge.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mindswap.pellet.KnowledgeBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.smarthome.knowledge.owl.OntologyCache;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

@Path("/ontology")
public class OwlApiService {

	private static final String DEFAULT_ONTOLOGY = "/WEB-INF/kb/SEOntology.owl";

	private OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();

	@Context
	ServletContext context;

	@Context
	HttpServletResponse servletResponse;

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/default")
	public Response getDefaultOntology() {

		// Get ontology source
		String ontologyPath = context.getRealPath(DEFAULT_ONTOLOGY);
		File ontologyFile = new File(ontologyPath);
		String ontologyName = ontologyFile.getName();

		// Load ontology
		if (!OntologyCache.instance.containsOntology(ontologyName)) {
			try {
				OWLOntology newOntology = ontologyManager.loadOntologyFromOntologyDocument(ontologyFile);
				System.out.println("ontology id: " + newOntology.getOntologyID());
				OntologyCache.instance.setOntology(ontologyName, newOntology);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		OWLOntology owlOntology = OntologyCache.instance.getOntology(ontologyName);
		String owlXml = getOwl2XmlRepresentation(owlOntology);
		return Response.ok(owlXml).build();
	}
	
	public void test(OWLOntology owlOntology) {
		PelletReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
		PelletReasoner reasoner = reasonerFactory.createReasoner(owlOntology);
		
		KnowledgeBase knowledgeBase = reasoner.getKB();
		knowledgeBase.realize();
		knowledgeBase.printClassTree();		

		// get the instances of a class
		PrefixManager pm = new DefaultPrefixManager("http://www.semanticweb.org/ontologies/2012/7/SimpleSE");
		
		boolean directSubclasses = true;
		OWLClass owlThing = ontologyManager.getOWLDataFactory().getOWLClass("#Device", pm);
		NodeSet<OWLClass> subClses = reasoner.getSubClasses(owlThing, directSubclasses);
		System.out.println(subClses.getNodes().size());

		OWLClass owlSensorDevice = ontologyManager.getOWLDataFactory().getOWLClass("#SensorDevice", pm);
		directSubclasses = false;
		NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(owlSensorDevice, directSubclasses);
		System.out.println(instances.getNodes().size());
		
		OWLObjectProperty containsDevice = ontologyManager.getOWLDataFactory().getOWLObjectProperty("#containsDevice", pm);
		OWLNamedIndividual locationSensorBedroom = ontologyManager.getOWLDataFactory().getOWLNamedIndividual(":kitchen", pm);
		
		NodeSet<OWLNamedIndividual> blubb = reasoner.getObjectPropertyValues(locationSensorBedroom, containsDevice);
		System.out.println(blubb.getNodes().size());
	}

	private String getOwl2XmlRepresentation(OWLOntology owlOntology) {
		OutputStream xmlOntology = new OutputStream() {
			private StringBuilder stringBuilder = new StringBuilder();

			@Override
			public void write(int b) throws IOException {
				stringBuilder.append((char) b);
			}

			public String toString() {
				return stringBuilder.toString();
			}
		};
		try {
			OWLXMLOntologyFormat owlXmlFormat = new OWLXMLOntologyFormat();
			ontologyManager.saveOntology(owlOntology, owlXmlFormat, xmlOntology);
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
		return xmlOntology.toString();
	}

}
