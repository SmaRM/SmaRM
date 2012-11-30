package org.smarthome.gwt.coreapp.client.ui.widgets;

import org.smarthome.gwt.coreapp.client.OntologyService;
import org.smarthome.gwt.coreapp.client.OntologyServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class OntologyTab extends VLayout {

    private final OntologyServiceAsync ontologyService = GWT.create(OntologyService.class);

    private final TextAreaItem ontologyEditingArea;
    private final TextAreaItem queryEditingArea;

    private static boolean owlIsActive = false;

    public OntologyTab() {
        super();

        final DynamicForm ontologyEditingForm = new DynamicForm();
        ontologyEditingForm.setWidth100();

        ontologyEditingArea = new TextAreaItem();
        ontologyEditingArea.setTitle("Ontology");
        ontologyEditingArea.setTitleOrientation(TitleOrientation.TOP);
        ontologyEditingArea.setWidth(800);
        ontologyEditingArea.setHeight(600);

        final DynamicForm queryEditingForm = new DynamicForm();
        queryEditingForm.setWidth100();
        queryEditingArea = new TextAreaItem();
        queryEditingArea.setTitle("Query");
        queryEditingArea.setTitleOrientation(TitleOrientation.TOP);
        queryEditingArea.setWidth(800);
        queryEditingArea.setHeight(50);

        ontologyEditingForm.setItems(ontologyEditingArea);
        queryEditingForm.setItems(queryEditingArea);

        final IButton btnGetActiveOwlOntology = new IButton("Load OWL ontology");
        btnGetActiveOwlOntology.setWidth(110);
        btnGetActiveOwlOntology.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                loadActiveOwlOntology();
            }
        });

        final IButton btnGetActiveAceOntology = new IButton("Load ACE ontology");
        btnGetActiveAceOntology.setWidth(110);
        btnGetActiveAceOntology.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                loadActiveAceOntology();
            }
        });

        final IButton btnVerbalizeToOwl = new IButton("Verbalize");
        btnVerbalizeToOwl.setWidth(110);
        btnVerbalizeToOwl.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                verbalizeOntology();
            }
        });

        final IButton btnAnswerAceQuery = new IButton("Answer ACE Query");
        btnAnswerAceQuery.setWidth(110);
        btnAnswerAceQuery.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                answerQuery();
            }
        });

        HLayout hLayout = new HLayout();
        hLayout.setWidth100();
        hLayout.addMember(btnGetActiveOwlOntology);
        hLayout.addMember(btnGetActiveAceOntology);
        hLayout.addMember(btnAnswerAceQuery);
        hLayout.addMember(btnVerbalizeToOwl);        

        addMember(ontologyEditingForm);
        addMember(queryEditingForm);
        addMember(hLayout);
    }

    private void loadActiveOwlOntology() {
        ontologyService.getOwlOntolology(new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("Data service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String result) {
                ontologyEditingArea.setValue(result);
                owlIsActive = true;
            }
        });
    }

    private void loadActiveAceOntology() {
        ontologyService.getRaceOntology(new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("Data service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String result) {
                ontologyEditingArea.setValue(result);
                owlIsActive = false;
            }
        });
    }

    private void verbalizeOntology() {
        final String owlXml = ontologyEditingArea.getValueAsString();
        if (owlIsActive) {
            ontologyService.translateOwlXmlToAce(owlXml, new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    SC.warn("Ontology service not available.\n" + caught.getMessage());
                }

                public void onSuccess(String result) {
                    ontologyEditingArea.setValue(result);
                }
            });
        }
    }

    public void answerQuery() {
        final String aceAxioms = ontologyEditingArea.getValueAsString();
        final String aceQuery = queryEditingArea.getValueAsString();
        ontologyService.answerRaceQuery(aceAxioms, aceQuery, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("RACE service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String result) {
                SC.say(result);
            }
        });
    }
}
