package org.smarthome.shared.weather;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class WeatherInformation implements Serializable {

    private static final long serialVersionUID = -1L;

    private String city;
    private Date date;
    
    private UnitSystem unitSystem;
    
    private String conditition;
    private String temperature;
    private String humidity;
    private String windSpeed;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setTime(long time) {
        this.date.setTime(time);
    }

    public UnitSystem getUnitSystem() {
    	return this.unitSystem;
    }
    
    public void setUnitSystem(UnitSystem unitSystem) {
    	this.unitSystem = unitSystem;
    }
    
    public String getConditition() {
        return conditition;
    }

    public void setConditition(String conditition) {
        this.conditition = conditition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}