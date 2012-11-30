package org.smarthome.gwt.coreapp.client.ui;

import org.smarthome.gwt.coreapp.client.ui.widgets.TestMenu;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class ApplicationMenu extends HLayout {

    private static final int APPLICATION_MENU_HEIGHT = 27;

    private static final ToolStrip toolStrip = new ToolStrip();

    // Menu bar item
    private static final MenuItem refreshDataItem = new MenuItem("Refresh data");
    private static final MenuItem addDataItem = new MenuItem("Add new data");
    private static final MenuItem trainAndTestNB = new MenuItem("Build and Classify (Naive Bayes)");
    private static final MenuItem trainAndTestAR = new MenuItem("Build and Classify (AR)");
    
    private static final MenuItem weatherAnnotateItem = new MenuItem("Weather Annotation");
    
    private static final MenuItem owlxmlToAceItem = new MenuItem("OWL 2 XML to ACE");
    private static final MenuItem aceToOwlxml = new MenuItem("ACE to OWL 2 XML");
    
    
    private static final MenuItem defaultViewItem = new MenuItem("Default view");
    private static final MenuItem dashBoardViewItem = new MenuItem("Dashboard view");
    
    public ApplicationMenu() {

        super();
        setHeight(APPLICATION_MENU_HEIGHT);
        setWidth100();

        final Menu resourcesMenu = new Menu();
        resourcesMenu.addItem(refreshDataItem);
        resourcesMenu.addItem(addDataItem);
        resourcesMenu.addItem(trainAndTestNB);
        resourcesMenu.addItem(trainAndTestAR);
      
        final Menu toolsMenu = new Menu();
        toolsMenu.addItem(weatherAnnotateItem);
        toolsMenu.addItem(owlxmlToAceItem);
        toolsMenu.addItem(aceToOwlxml);
        
        final Menu viewMenu = new Menu();
        viewMenu.setData(defaultViewItem, dashBoardViewItem);
        
        final Menu testMenu = new TestMenu();

        final ToolStripMenuButton resourceMenuButton = new ToolStripMenuButton("Resources", resourcesMenu);
        final ToolStripMenuButton toolsMenuButton = new ToolStripMenuButton("Tools", toolsMenu);
        final ToolStripMenuButton viewMenuButton = new ToolStripMenuButton("View", viewMenu);
        final ToolStripMenuButton testMenuButton = new ToolStripMenuButton("Test", testMenu);
        
        // Create menu bar
        toolStrip.setWidth100();
        toolStrip.addMenuButton(resourceMenuButton);
        toolStrip.addMenuButton(toolsMenuButton);
        toolStrip.addMenuButton(viewMenuButton);
        toolStrip.addMenuButton(testMenuButton);

        addMember(toolStrip);
    }
        
    public void addRefreshDataClickHandler(ClickHandler handler) {
        refreshDataItem.addClickHandler(handler);
    }
    
    public void addTrainAndClassifyNaiveBayesHandler(ClickHandler handler) {
        trainAndTestNB.addClickHandler(handler);
    }
    
    public void addTrainAndClassifyARHandler(ClickHandler handler) {
        trainAndTestAR.addClickHandler(handler);
    }
    
    public void addWeatherAnnotationDataClickHandler(ClickHandler handler) {
        weatherAnnotateItem.addClickHandler(handler);
    }
    
    public void addOwlxmlToAceDataClickHandler(ClickHandler handler) {
        owlxmlToAceItem.addClickHandler(handler);
    }
    
    public void addAceToOwlxmlDataClickHandler(ClickHandler handler) {
        aceToOwlxml.addClickHandler(handler);
    }
    
    public void addDefaultViewClickHandler(ClickHandler handler) {
        defaultViewItem.addClickHandler(handler);
    }
    
    public void addDashboardViewClickHandler(ClickHandler handler) {
        dashBoardViewItem.addClickHandler(handler);
    }
}