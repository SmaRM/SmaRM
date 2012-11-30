package org.smarthome.gwt.coreapp.client.utils;

public class BrowserWindow {
    public static void open(String name, String url) {
        com.google.gwt.user.client.Window.open(url, name.replace(" ", "_"),
               "menubar=no," + 
               "location=false," + 
               "resizable=yes," + 
               "scrollbars=yes," + 
               "status=no," + 
               "dependent=true");
    }
}
