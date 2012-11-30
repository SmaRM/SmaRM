package org.smarthome.shared.datamodel;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the sensor_activity_entity database table.
 * 
 */
@Entity
@Table(name = "sensor_activity_entity")
@XmlRootElement
public class SensorActivityEntity implements DataEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	protected String activity;

	@Temporal(TemporalType.DATE)
	protected Date dateinfo;

	protected String location;

	protected String sensorid;

	protected String sensorvalue;

	@Temporal(TemporalType.TIME)
	protected Date timeinfo;
	
	protected String weathercondition;
	
	protected String outsidetemperature;
	
	protected String resources;
	
	protected String actions; 
	
	protected String misc;

	public SensorActivityEntity() {
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Date getDateinfo() {
		return this.dateinfo;
	}

	public void setDateinfo(Date dateinfo) {
		this.dateinfo = dateinfo;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSensorid() {
		return this.sensorid;
	}

	public void setSensorid(String sensorid) {
		this.sensorid = sensorid;
	}

	public String getSensorvalue() {
		return this.sensorvalue;
	}

	public void setSensorvalue(String sensorvalue) {
		this.sensorvalue = sensorvalue;
	}

	public Date getTimeinfo() {
		return this.timeinfo;
	}

	public void setTimeinfo(Date timeinfo) {
		this.timeinfo = timeinfo;
	}
	
	public String getWeathercondition() {
		return this.weathercondition;
	}

	public void setWeathercondition(String weathercondition) {
		this.weathercondition = weathercondition;
	}
	
	public String getOutsidetemperature() {
		return this.outsidetemperature;
	}

	public void setOutsidetemperature(String outsidetemperature) {
		this.outsidetemperature = outsidetemperature;
	}
	
	public String getResources() {
		return this.resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}
	
	public String getActions() {
		return this.actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}
	
	public String getMisc() {
		return this.misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}
	
	@Override
	public void init() {
		id = 0;
		dateinfo = new Date(0);
		timeinfo = new Date(0);
		location = sensorid = sensorvalue = "";
		activity = "";
		weathercondition = outsidetemperature = "";
		resources = actions = misc = "";
	}

	@Override
	public void update(DataEntity entity) {
		SensorActivityEntity newData = (SensorActivityEntity) entity;
		id = newData.id;
		dateinfo = newData.dateinfo;
		timeinfo = newData.timeinfo;
		location = newData.location;
		sensorid = newData.sensorid;
		sensorvalue = newData.sensorvalue;
		activity = newData.activity;
		weathercondition = newData.weathercondition;
		outsidetemperature = newData.outsidetemperature;
		resources = newData.resources;
		actions = newData.actions;
		misc = newData.misc;
	}

	public String arSvmFormat() {
		 return String.format("%1$tY-%1$tm-%1$te %2$tH:%2$tM:%2$tS:%2$tL %3$s %4$s %5$s %6$s %n", 
				 dateinfo,
				 timeinfo,
				 location,
				 sensorid,
				 sensorvalue,
				 activity);
	}
	
	public static final String VAR_ID = "ID";
	public static final String VAR_DATE = "Date";
	public static final String VAR_TIME = "Time";
	public static final String VAR_LOCATION = "Location";
	public static final String VAR_SENSORID = "Sensor ID";
	public static final String VAR_SENSORVAL = "Sensor Value";
	public static final String VAR_WEATHERCOND = "Weather Cond.";
	public static final String VAR_OUTSIDETEMP = "Outside Temp.";
	public static final String VAR_ACTIVITY = "Activity";
	public static final String VAR_RESOURCES = "Resources";
	public static final String VAR_ACTIONS = "Actions";

	@Override
	public Map<String, Object> getRecordProperties() {
		Map<String, Object> recordProperties = new LinkedHashMap<String, Object>();
		recordProperties.put(VAR_ID, id);
		recordProperties.put(VAR_DATE, dateinfo);
		recordProperties.put(VAR_TIME, timeinfo);
		recordProperties.put(VAR_LOCATION, location);
		recordProperties.put(VAR_SENSORID, sensorid);
		recordProperties.put(VAR_SENSORVAL, sensorvalue);
		recordProperties.put(VAR_WEATHERCOND, weathercondition);
		recordProperties.put(VAR_OUTSIDETEMP, outsidetemperature);
		recordProperties.put(VAR_ACTIVITY, activity);
		recordProperties.put(VAR_RESOURCES, resources);
		recordProperties.put(VAR_ACTIONS, actions);
		return recordProperties;
	}
}