package org.smarthome.gwt.coreapp.client.ui.widgets;

import org.smarthome.gwt.coreapp.client.utils.BrowserWindow;
import org.smarthome.gwt.coreapp.shared.ServiceUrl;

import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class TestMenu extends Menu {
    
    public TestMenu() {

        final MenuItem testViewKnowledgeIndex = new MenuItem("View Knowledge JSP");
        testViewKnowledgeIndex.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                BrowserWindow.open("SmaRM Knowledge Services", ServiceUrl.KNOWLEDGE_INDEX);
            }
        });

        final MenuItem testSensorActivityAll = new MenuItem("View Sensor Activity Data (JSON)");
        testSensorActivityAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                BrowserWindow.open("SmaRM Sensor Activty Data", ServiceUrl.SENSOR_ACTIVITY_DATA_ALL);
            }
        });

        final MenuItem testSensorNewAll = new MenuItem("View Sensor New Data (JSON)");
        testSensorActivityAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                BrowserWindow.open("SmaRM Sensor New Data", ServiceUrl.SENSOR_NEW_DATA_ALL);
            }
        });
        
        final MenuItem testFeedbackAll = new MenuItem("View Feedback Data (JSON)");
        testSensorActivityAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                BrowserWindow.open("SmaRM Feedback Data", ServiceUrl.FEEDBACK_ALL);
            }
        });

        addItem(testSensorActivityAll);
        addItem(testSensorNewAll);
        addItem(testFeedbackAll);
    }
}
