package org.smarthome.gwt.coreapp.client.ui.widgets;

import org.smarthome.gwt.coreapp.client.ConfigurationService;
import org.smarthome.gwt.coreapp.client.ConfigurationServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ConfigurationTab extends VLayout {
    
    private final ConfigurationServiceAsync configService = GWT.create(ConfigurationService.class);
       
    private static final ComboBoxItem cbItem = new ComboBoxItem();
    private static TextAreaItem configTextArea = new TextAreaItem();  
    
    private static final TextItem homeTown = new TextItem();
    private static final TextItem countryCode = new TextItem();
    private static final TextItem stateInfo = new TextItem();
    
    public ConfigurationTab() {
        super();
        
        cbItem.setTitle("Machine Learning Algorithm");
        
        homeTown.setTitle("State");
        countryCode.setTitle("Country");
        stateInfo.setTitle("State");
        
        final DynamicForm form1 = new DynamicForm();    
        form1.setWidth(250);  
        form1.setItems(cbItem, homeTown, countryCode, stateInfo);
        
        configTextArea.setTitle("Configuration");
        configTextArea.setTitleOrientation(TitleOrientation.TOP);
        configTextArea.setWidth(250);
        configTextArea.setHeight(150);
       
        final DynamicForm form2 = new DynamicForm(); 
        form2.setWidth(250);  
        form2.setItems(configTextArea);

        final IButton btnLoadConfiguration = new IButton("Load configuration");
        btnLoadConfiguration.setWidth(110);
        btnLoadConfiguration.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                loadConfiguration();
            }
        });
        
        final IButton btnSaveConfiguration = new IButton("Save configuration");
        btnSaveConfiguration.setWidth(110);
        btnSaveConfiguration.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                saveConfiguration();
            }
        });
              
        HLayout hLayoutBottom = new HLayout();
        hLayoutBottom.setWidth100();
        hLayoutBottom.addMember(btnLoadConfiguration);
        hLayoutBottom.addMember(btnSaveConfiguration);
        
        loadDefaultConfiguration();
        
        addMember(form1);
        addMember(form2);
        addMember(hLayoutBottom);
    }
    
    private void loadDefaultConfiguration() {
        String algorithms[] = new String[] { "NB/SVM" };
        cbItem.setValueMap(algorithms);
        cbItem.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                String selectedAlgorithm = cbItem.getValueAsString();
                selectedAlgorithmChanged(selectedAlgorithm);
            }
        });
        cbItem.setValue(algorithms[0]);
        
        homeTown.setValue("Pullman");
        countryCode.setValue("US");
        stateInfo.setValue("WA");
    }
    
    private void loadConfiguration() {

        configService.getDatabaseConfiguration(new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("Config service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String configText) {
                configTextArea.setValue(configText);
            }
        });

    }
    
    private void saveConfiguration() {
        final String dbConfig = configTextArea.getValueAsString();

        configService.setDatabaseConfiguration(dbConfig, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("Config service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String configText) {
                configTextArea.setValue(configText);
                SC.say("Configuration updated successfully.");
            }
        });

    }
    
    private void selectedAlgorithmChanged(String newAlgorithm) {
        // TODO: future use case
    }
}
