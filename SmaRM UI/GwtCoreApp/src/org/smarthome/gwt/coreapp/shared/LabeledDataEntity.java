package org.smarthome.gwt.coreapp.shared;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the labeled_data database table.
 * 
 * @PersistenceContext(unitName="ResourceDataEntity", type=PersistenceContextType.TRANSACTION)
 * 
 */
@Entity
@Table(name = "labeled_data")
@XmlRootElement
public class LabeledDataEntity implements DataEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	private String activity;

	private Integer duration;

	@Column(name = "condition_inside")
	private String conditionInside;

	@Column(name = "condition_outside")
	private String conditionOutside;

	@Column(name = "energy_consumption")
	private double energyConsumption;

	private String locations;

	@Column(name = "water_consumption")
	private double waterConsumption;

	private double costs;

	private String actions;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getConditionInside() {
		return conditionInside;
	}

	public void setConditionInside(String conditionInside) {
		this.conditionInside = conditionInside;
	}

	public String getConditionOutside() {
		return conditionOutside;
	}

	public void setConditionOutside(String conditionOutside) {
		this.conditionOutside = conditionOutside;
	}

	public double getEnergyConsumption() {
		return energyConsumption;
	}

	public void setEnergyConsumption(double energyConsumption) {
		this.energyConsumption = energyConsumption;
	}

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public double getWaterConsumption() {
		return waterConsumption;
	}

	public void setWaterConsumption(double waterConsumption) {
		this.waterConsumption = waterConsumption;
	}

	public double getCosts() {
		return costs;
	}

	public void setCosts(double costs) {
		this.costs = costs;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public LabeledDataEntity() {
	}

	@Override
	public void init() {
		id = duration = 0;
		userId = activity = conditionInside = conditionOutside = locations = actions = "";
		dateTime = new Date(0);
		energyConsumption = waterConsumption = costs = 0;
	}
	
	@Override
	public void update(DataEntity entity) {
		LabeledDataEntity newData = (LabeledDataEntity) entity;
		userId = newData.userId;
		dateTime = newData.dateTime;
		activity = newData.activity;
		locations = newData.locations;
		duration = newData.duration;
		conditionInside = newData.conditionInside;
		conditionOutside = newData.conditionOutside;
		energyConsumption = newData.energyConsumption;
		waterConsumption = newData.waterConsumption;
		costs = newData.costs;
	}
	
	public static final String VAR_ID = "ID";
	public static final String VAR_USERID = "User ID";
	public static final String VAR_DATETIME = "Date-Time";
	public static final String VAR_ACTIVITY = "Activity";
	public static final String VAR_DURATION = "Duration";
	public static final String VAR_CONDITION_INSIDE = "Condition Inside";
	public static final String VAR_CONDITION_OUTSIDE = "Condition Outside";
	public static final String VAR_LOCATIONS = "Locations";
	public static final String VAR_ACTIONS = "Actions";
	public static final String VAR_ENERGYCONSUMPTION = "Energy Consumption";
	public static final String VAR_WATERCONSUMPTION = "Water Consumption";
	public static final String VAR_COSTS = "Costs";
	
	@Override
	public Map<String, Object> getRecordProperties() {
		Map<String, Object> recordProperties = new LinkedHashMap<String, Object>();
		recordProperties.put(VAR_ID, id);
		recordProperties.put(VAR_USERID, userId);
		recordProperties.put(VAR_DATETIME, dateTime);
		recordProperties.put(VAR_ACTIVITY, activity);
		recordProperties.put(VAR_DURATION, duration);
		recordProperties.put(VAR_CONDITION_INSIDE, conditionInside);
		recordProperties.put(VAR_CONDITION_OUTSIDE, conditionOutside);
		recordProperties.put(VAR_LOCATIONS, locations);
		recordProperties.put(VAR_ACTIONS, actions);
		recordProperties.put(VAR_ENERGYCONSUMPTION, energyConsumption);
		recordProperties.put(VAR_WATERCONSUMPTION, waterConsumption);
		recordProperties.put(VAR_COSTS, costs);
		return recordProperties;
	}
}