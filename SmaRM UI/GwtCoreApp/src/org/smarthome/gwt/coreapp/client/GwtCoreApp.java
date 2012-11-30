package org.smarthome.gwt.coreapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtCoreApp implements EntryPoint {

    public final String VIEW_SMARTGWT_HOME = "home";
    public final String VIEW_DASHBOARD_HOME = "dash";
    
    private SmartGwtUi smartGwtUi;
    
    public void onModuleLoad() {
        Window.enableScrolling(false);
        Window.setMargin("0px");
        
        smartGwtUi = new SmartGwtUi();
        
        // Add default layout to root container
        RootPanel.get().add(smartGwtUi);
    }
}
