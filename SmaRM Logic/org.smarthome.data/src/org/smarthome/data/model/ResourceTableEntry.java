package org.smarthome.data.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement(name="ResourceEntry")
@Entity
public class ResourceTableEntry implements Serializable {
	
	private static final long serialVersionUID = -5031928097260593793L;

	@XmlTransient
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@XmlTransient
	private Date date;
		
	private String location;
	
	private String activityLabel;
	
	private int durationMin;
	
	private double energyConsumptionKwh;
	
	private double waterConsumptionCm3;
	
	private double energyCostsEur;
	
	public ResourceTableEntry() {
		
	}

	public String getDateTime() {
		return DATEFORMAT.format(date);
	}

	public void setDateTime(String datetime) throws ParseException {
		this.date = DATEFORMAT.parse(datetime);
	}
	
	public void setDateTime(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public int getDurationMin() {
		return durationMin;
	}

	public void setDurationMin(int durationMin) {
		this.durationMin = durationMin;
	}

	public double getEnergyConsumptionKwh() {
		return energyConsumptionKwh;
	}

	public void setEnergyConsumptionKwh(double energyConsumptionKwh) {
		this.energyConsumptionKwh = energyConsumptionKwh;
	}

	public double getWaterConsumptionCm3() {
		return waterConsumptionCm3;
	}

	public void setWaterConsumptionCm3(double waterConsumptionCm3) {
		this.waterConsumptionCm3 = waterConsumptionCm3;
	}

	public double getEnergyCostsEur() {
		return energyCostsEur;
	}
	
	public void setEnergyCostsEur(double energyCostsEur) {
		this.energyCostsEur = energyCostsEur;
	}

	public void calculateEnergyCostsEur(double energyPrice, double waterPrice) {
		setEnergyCostsEur(energyConsumptionKwh * energyPrice + waterConsumptionCm3 * waterPrice);
	}
}
