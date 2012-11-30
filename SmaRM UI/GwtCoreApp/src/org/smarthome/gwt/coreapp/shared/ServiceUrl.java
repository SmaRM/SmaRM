package org.smarthome.gwt.coreapp.shared;

public class ServiceUrl {
    
    public static final String SERVER = "http://localhost:8080";
    
    /* CONFIGURATION SERVICE COMPONENTS */
    public static final String CONFIGURATION_BASE = SERVER + "/org.smarthome.configuration/rest";
    
    public static final String CONFIGURATION_DB = CONFIGURATION_BASE + "/dbconfig";
    public static final String CONFIGURATION_DB_UPDATE = CONFIGURATION_DB + "/update";
    
    /* DATA SERVICE COMPONENTS */
    public static final String DATA_BASE = SERVER + "/org.smarthome.data/rest";
    
    public static final String LABELEDDATA_ALL = DATA_BASE + "/resources/labeleddata.json/all";
    
    public static final String SENSOR_ACTIVITY_DATA_ALL = DATA_BASE + "/resources/sensoractivity.json/all";
    public static final String SENSOR_ACTIVITY_DATA_FIRST30 = DATA_BASE + "/resources/sensoractivity.json/30";
    
    public static final String SENSOR_NEW_DATA_ALL = DATA_BASE + "/resources/sensornew.json/all";
    public static final String SENSOR_NEW_DATA_ADD = DATA_BASE + "/resources/sensornew/add/json";
    
    public static final String FEEDBACK_ALL = DATA_BASE + "/resources/feedback.json/all";
    
    /* LEARNING SERVICE COMPONENTS */
    public static final String AR_INDEX = "org.smarthome.activityrecognition";
    public static final String AR_BASE = AR_INDEX + "/rest/";
    public static final String AR_TRAIN_SENSORACTIVITY = AR_BASE + "/train/sensoractivity";
    public static final String AR_CLASSIFY_SENSORACTIVITY = AR_BASE + "/classify/sensoractivity";
    public static final String AR_CLASSIFY_SENSORNEW = AR_BASE + "/classify/sensornew";
    
    public static final String NB_INDEX = "org.smarthome.machinelearning";
    public static final String NB_BASE = NB_INDEX + "/rest/";
    public static final String NB_TRAINANDCLASSIFY = NB_BASE + "/trainandclassify";
    
    /* KNOWLEDGE SERVICE COMPONENTS */
    public static final String KNOWLEDGE_INDEX = SERVER + "/org.smarthome.knowledge";
    public static final String KNOWLEDGE_BASE = KNOWLEDGE_INDEX + "/rest";
    public static final String OWLVERBALIZER = KNOWLEDGE_BASE + "/verbalize";
    public static final String RACE = KNOWLEDGE_BASE + "/race";
    public static final String APE = KNOWLEDGE_BASE + "/ape";
    public static final String ACERULES = KNOWLEDGE_BASE + "/acerules";
    public static final String ONTOLOGY = KNOWLEDGE_BASE + "/ontology";
    
    /* -- RACE METHODS */
    public static final String DEFAULT_RACEONTOLOGY = RACE + "/ontology/view";
    public static final String DEFAULT_RACEONTOLOGY_UPDATE = RACE + "/ontology/update";
    
    public static final String RACE_QUERY = RACE + "/query";
    public static final String RACE_ACTIVITYRESOURCE = RACE_QUERY + "/resource";
    public static final String RACE_ACTIVITYACTION = RACE_QUERY + "/action";
    
    /* -- OWL API */
    public static final String DEFAULT_OWLONTOLOGY = ONTOLOGY + "/default";
    
    public static final String WEATHER_INDEX = SERVER + "/org.smarthome.weather";
    public static final String WEATHER_BASE = WEATHER_INDEX + "/rest";
    private static final String WEATHER_ANNOTATE = WEATHER_BASE + "/annotate";
    public static final String WEATHER_ANNOTATE_SENSOR_ACTIVITY = WEATHER_ANNOTATE + "/sensoractivity";
    public static final String WEATHER_ANNOTATE_SENSOR_NEW = WEATHER_ANNOTATE + "/sensornew";
}
