package org.smarthome.gwt.coreapp.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.smarthome.gwt.coreapp.client.ui.ApplicationArea;
import org.smarthome.gwt.coreapp.client.ui.ApplicationMenu;
import org.smarthome.gwt.coreapp.client.ui.HeaderArea;
import org.smarthome.gwt.coreapp.client.ui.widgets.DashboardVisualization;
import org.smarthome.gwt.coreapp.client.utils.BrowserWindow;
import org.smarthome.gwt.coreapp.shared.DataEntity;
import org.smarthome.gwt.coreapp.shared.FeedbackEntity;
import org.smarthome.gwt.coreapp.shared.SensorActivityEntity;
import org.smarthome.gwt.coreapp.shared.SensorNewEntity;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.kfuntak.gwt.json.serialization.client.Serializer;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class SmartGwtUi extends VerticalPanel {
    private static final String HEADER_HEIGHT = "25%";

    private final DataServiceAsync dataService = GWT.create(DataService.class);
    private final LearningServiceAsync learningService = GWT.create(LearningService.class);

    private final Serializer jsonSerializer = (Serializer) GWT.create(Serializer.class);

    private final ApplicationArea applicationArea = new ApplicationArea();
    private final HorizontalPanel southPanel = new HorizontalPanel();

    private final DashboardVisualization dashBoardUi = new DashboardVisualization();

    private Widget selectedViewArea;

    public enum View {
        DEFAULT, DASHBOARD
    }

    public SmartGwtUi() {
        super();

        // main layout occupies the whole area
        setWidth("100%");
        setHeight("100%");

        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setHeight(HEADER_HEIGHT);

        final ApplicationMenu applicationMenu = new ApplicationMenu();
        applicationMenu.addRefreshDataClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                refreshData();
            }
        });
        
        applicationMenu.addTrainAndClassifyNaiveBayesHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                trainAndClassifiyNaiveBayes();
            }
        });
        
        applicationMenu.addTrainAndClassifyARHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                trainAndClassifySVM();
            }
        });


        applicationMenu.addDefaultViewClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                updateView(View.DEFAULT);
            }
        });

        applicationMenu.addWeatherAnnotationDataClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                annotateWeatherData();
            }
        });

        applicationMenu.addDashboardViewClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                updateView(View.DASHBOARD);
            }
        });

        // header
        final HeaderArea headerArea = new HeaderArea();
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(headerArea);
        vPanel.add(applicationMenu);
        hPanel.add(vPanel);

        // navigation (optional)
        // final NavigationArea navigationArea = new NavigationArea();
        // navigationArea.setWidth("15%");
        // navigationArea.setHeight("75%");
        // southPanel.add(navigationArea);

        // main area
        applicationArea.setWidth("100%");

        southPanel.add(applicationArea);

        selectedViewArea = applicationArea;

        add(hPanel);
        add(southPanel);
    }

    public void updateView(View view) {
        southPanel.remove(selectedViewArea);

        if (view.equals(View.DASHBOARD)) {
            dashBoardUi.refresh();
            selectedViewArea = dashBoardUi;
        } else {
            selectedViewArea = applicationArea;
        }

        southPanel.add(selectedViewArea);
    }

    public void refreshData() {
        String selectedTab = applicationArea.getSelectedTabTitle();
        if (selectedTab.equals(applicationArea.SENSOR_ACTIVITY_TAB)) {
            dataService.getSensorActivityData(new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    SC.warn("Data service not available.\n" + caught.getMessage());
                }

                public void onSuccess(String result) {
                    updateSensorActivityTable(result);
                }
            });
        } else if (selectedTab.equals(applicationArea.SENSOR_NEW_TAB)) {
            dataService.getSensorNewData(new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    SC.warn("Data service not available.\n" + caught.getMessage());
                }

                public void onSuccess(String result) {
                    updateSensorNewTable(result);
                }
            });
        } else if (selectedTab.equals(applicationArea.FEEDBACK_TAB)) {
            dataService.getFeedbackData(new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    SC.warn("Data service not available.\n" + caught.getMessage());
                }

                public void onSuccess(String result) {
                    updateFeedbackTable(result);
                }
            });
        }
    }

    private void trainAndClassifiyNaiveBayes() {
        learningService.trainAndClassifyNaiveBayes(new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("Learning service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String result) {
                applicationArea.getDebugTab().setText(result);
                applicationArea.setActiveTab(applicationArea.FEEDBACK_TAB);
                refreshData();
            }
        });
    }
    
    private void trainAndClassifySVM() {
        learningService.trainAndClassifyAR(new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                SC.warn("Learning service not available.\n" + caught.getMessage());
            }

            public void onSuccess(String result) {
                applicationArea.getDebugTab().setText(result);
            }
        });
    }

    private void annotateWeatherData() {
        final String selectedTab = applicationArea.getSelectedTabTitle();
        SC.ask("This service does only support 10 requests per minute! Continue?", new BooleanCallback() {
            public void execute(Boolean value) {
                if (value != null && value) {
                    if (selectedTab.equals(applicationArea.SENSOR_ACTIVITY_TAB)) {
                        BrowserWindow.open("SmaRM Weather Annotation", ServiceUrl.WEATHER_ANNOTATE_SENSOR_ACTIVITY);
                    } else if (selectedTab.equals(applicationArea.SENSOR_NEW_TAB)) {
                        BrowserWindow.open("SmaRM Weather Annotation", ServiceUrl.WEATHER_ANNOTATE_SENSOR_NEW);
                    }
                } else {
                    BrowserWindow.open("SmaRM Weather Services", ServiceUrl.WEATHER_INDEX);
                }
            }
        });
    }

    public void updateSensorActivityTable(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONArray jsonArray = jsonValue.isArray();

        Collection<SensorActivityEntity> sensorActivityEntries = deserializeFromJSON(jsonArray,
                SensorActivityEntity.class);
        applicationArea.getSensorActivityTab().clearEntities();
        for (DataEntity entity : sensorActivityEntries) {
            applicationArea.getSensorActivityTab().addEntity(entity);
        }
        applicationArea.getSensorActivityTab().setLastUpdated(new Date());
    }

    public void updateSensorNewTable(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONArray jsonArray = jsonValue.isArray();

        Collection<SensorNewEntity> sensorNewEntries = deserializeFromJSON(jsonArray, SensorNewEntity.class);
        applicationArea.getSensorNewTab().clearEntities();
        for (DataEntity entity : sensorNewEntries) {
            applicationArea.getSensorNewTab().addEntity(entity);
        }
        applicationArea.getSensorNewTab().setLastUpdated(new Date());
    }

    public void updateFeedbackTable(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONArray jsonArray = jsonValue.isArray();

        Collection<FeedbackEntity> feedbackEntries = deserializeFromJSON(jsonArray, FeedbackEntity.class);
        applicationArea.getFeedbackTab().clearEntities();
        for (DataEntity entity : feedbackEntries) {
            applicationArea.getFeedbackTab().addEntity(entity);
        }
        applicationArea.getFeedbackTab().setLastUpdated(new Date());
    }

    public void updateSensorFeedbackTable(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONArray jsonArray = jsonValue.isArray();

        Collection<SensorActivityEntity> sensorActivityEntries = deserializeFromJSON(jsonArray,
                SensorActivityEntity.class);
        for (DataEntity entity : sensorActivityEntries) {
            applicationArea.getSensorActivityTab().addEntity(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends DataEntity> T deserializeFromJSON(JSONValue pojo, Class<T> targetClass) {
        String json = pojo.toString();
        Object instance = jsonSerializer.deSerialize(json, targetClass.getName());
        return (T) instance;
    }

    public <T extends DataEntity> Collection<T> deserializeFromJSON(JSONArray jsonArray, Class<T> targetClass) {
        Collection<T> result = new ArrayList<T>();

        JSONValue jsonValue;
        T dataEntry;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonValue = jsonArray.get(i);
            if (jsonValue != null) {
                dataEntry = deserializeFromJSON(jsonValue, targetClass);
                result.add(dataEntry);
            }
        }

        return result;
    }
}
