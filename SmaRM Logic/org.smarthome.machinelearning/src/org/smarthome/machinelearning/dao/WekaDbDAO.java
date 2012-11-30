package org.smarthome.machinelearning.dao;

import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class WekaDbDAO {

	// DB connection
	private static final String DB_NAME = "postgres";
	private static final String DB_URL = "jdbc:postgresql://SERVER:PORT/" + DB_NAME;
	// DB authentication
	private static final String USER = "postgres";
	private static final String PASSWD = "";

	private static final String SQL_QUERY = "SELECT TO_CHAR(dateinfo, 'Day') as weekday, " +
			"TO_CHAR(timeinfo, 'HH24') as timeinfo, location, sensorid, sensorvalue, weathercondition, outsidetemperature, activity " +
			"FROM ";
	
	private static final String TRAINING_TABLE = "sensor_activity_entity"; 
	private static final String TEST_TABLE = "sensor_new_entity";
	
	public static Instances getTrainingInstances() throws Exception {
		Instances instances = null;

		/* Instances from Database */
		InstanceQuery query = new InstanceQuery();
		query.setDatabaseURL(DB_URL);
		query.setUsername(USER);
		query.setPassword(PASSWD);
		query.setQuery(SQL_QUERY + TRAINING_TABLE);
		
		instances = query.retrieveInstances();
		instances.setClassIndex(instances.numAttributes() - 1);
		
		query.close();

		return instances;
	}
	
	public static Instances getTestInstances() throws Exception {
		Instances instances = null;

		/* Instances from Database */
		InstanceQuery query = new InstanceQuery();
		query.setDatabaseURL(DB_URL);
		query.setUsername(USER);
		query.setPassword(PASSWD);
		query.setQuery(SQL_QUERY + TEST_TABLE);
		
		instances = query.retrieveInstances();
		instances.setClassIndex(instances.numAttributes() - 1);
		
		query.close();

		return instances;
	}
}
