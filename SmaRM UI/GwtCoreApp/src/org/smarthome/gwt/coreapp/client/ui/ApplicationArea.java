package org.smarthome.gwt.coreapp.client.ui;

import org.smarthome.gwt.coreapp.client.ui.widgets.ConfigurationTab;
import org.smarthome.gwt.coreapp.client.ui.widgets.DebugTab;
import org.smarthome.gwt.coreapp.client.ui.widgets.OntologyTab;
import org.smarthome.gwt.coreapp.client.ui.widgets.ResourceTab;
import org.smarthome.gwt.coreapp.shared.FeedbackEntity;
import org.smarthome.gwt.coreapp.shared.SensorActivityEntity;
import org.smarthome.gwt.coreapp.shared.SensorNewEntity;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class ApplicationArea extends VerticalPanel {

    public final String SENSOR_ACTIVITY_TAB = "Sensor Activity Model";
    public final String SENSOR_NEW_TAB = "Sensor New Data";
    public final String FEEDBACK_TAB = "Feedback";

    final TabSet topTabSet = new TabSet();
    final ResourceTab sensorActivityTab = new ResourceTab(new SensorActivityEntity());
    final ResourceTab sensorNewTab = new ResourceTab(new SensorNewEntity());
    final ResourceTab feedbackTab = new ResourceTab(new FeedbackEntity());
    
    final DebugTab debugTab = new DebugTab();

    final OntologyTab ontologyTab = new OntologyTab();
    final ConfigurationTab configTab = new ConfigurationTab();

    public ApplicationArea() {
        super();

        topTabSet.setTabBarPosition(Side.TOP);
        topTabSet.setTabBarAlign(Side.LEFT);

        topTabSet.setOverflow(Overflow.HIDDEN);
        topTabSet.setShowResizeBar(true);
        topTabSet.setPadding(5);
        topTabSet.setWidth100();
        topTabSet.setHeight(800);
        topTabSet.setMaxHeight(900);

        Canvas preferenceTab = new Canvas();
        preferenceTab.setWidth100();
        preferenceTab.setHeight100();
        preferenceTab.addChild(configTab);

        addTabToTopTabset(SENSOR_ACTIVITY_TAB, sensorActivityTab, false);
        addTabToTopTabset(SENSOR_NEW_TAB, sensorNewTab, false);
        addTabToTopTabset(FEEDBACK_TAB, feedbackTab, false);
        addTabToTopTabset("Knowledge", ontologyTab, false);
        addTabToTopTabset("Preferences", configTab, false);
        addTabToTopTabset("Debug", debugTab, false);
        addTabToTopTabset("Simulation", new HTMLFlow("<h2>Coming soon...</h2>"), true);

        topTabSet.selectTab(0);

        add(topTabSet);
    }

    public String getSelectedTabTitle() {
        return topTabSet.getSelectedTab().getTitle();
    }

    public void setActiveTab(String id) {
        topTabSet.selectTab(id);
    }
    
    public DebugTab getDebugTab() {
        return debugTab;
    }

    public ResourceTab getSensorActivityTab() {
        return sensorActivityTab;
    }

    public ResourceTab getSensorNewTab() {
        return sensorNewTab;
    }

    public ResourceTab getFeedbackTab() {
        return feedbackTab;
    }

    public OntologyTab getOntologyTab() {
        return ontologyTab;
    }

    public ConfigurationTab getConfigurationTab() {
        return configTab;
    }

    private void addTabToTopTabset(String title, Canvas pane, boolean closable) {
        Tab tab = createTab(title, pane, closable);
        topTabSet.addTab(tab);
        topTabSet.selectTab(tab);
    }

    private Tab createTab(String title, Canvas pane, boolean closable) {
        Tab tab = new Tab(title);
        tab.setCanClose(closable);
        tab.setPane(pane);
        return tab;
    }
}
