package org.smarthome.knowledge.owl;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLOntology;

public enum OntologyCache {
	instance;

	// map: key = ontology resource identifier, value = OWL ontology
	private Map<String, OWLOntology> ontologies;
	
	private OntologyCache() {
		ontologies = new HashMap<String, OWLOntology>();
	}
	
	public OWLOntology getOntology(String name) {
		return ontologies.get(name);
	}
	
	public boolean containsOntology(String name) {
		return ontologies.containsKey(name);
	}
	
	public void setOntology(String name, OWLOntology ontology) {
		ontologies.put(name, ontology);
	}
}
