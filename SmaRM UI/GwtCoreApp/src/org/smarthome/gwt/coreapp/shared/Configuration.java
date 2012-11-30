package org.smarthome.gwt.coreapp.shared;

public class Configuration {
       
    private String homeLocation = "Pullman";
    private String homeState = "WA";
    private String homeCountry = "US";  
    
    // ML preferences
    private String learningAlgorithm = "NB + SVM";
       
    public String getLearningAlgorithm() {
        return learningAlgorithm;
    }
    
    public void setLearningAlgorithm(String learningAlgorithm) {
        this.learningAlgorithm = learningAlgorithm;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getHomeState() {
        return homeState;
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }
}
