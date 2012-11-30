package org.smarthome.gwt.coreapp.shared;

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


/**
 * The persistent class for the feedback_entity database table.
 * 
 */
@Entity
@Table(name="feedback_entity")
public class FeedbackEntity implements DataEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String classinfo;

	@Temporal(TemporalType.DATE)
	private Date dateinfo;

	private String message;

	private String priority;

	private String reflink;

	@Temporal(TemporalType.TIME)
	private Date timeinfo;

	private String typeinfo;

	public FeedbackEntity() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassinfo() {
		return this.classinfo;
	}

	public void setClassinfo(String classinfo) {
		this.classinfo = classinfo;
	}

	public Date getDateinfo() {
		return this.dateinfo;
	}

	public void setDateinfo(Date dateinfo) {
		this.dateinfo = dateinfo;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getReflink() {
		return this.reflink;
	}

	public void setReflink(String reflink) {
		this.reflink = reflink;
	}

	public Date getTimeinfo() {
		return this.timeinfo;
	}

	public void setTimeinfo(Date timeinfo) {
		this.timeinfo = timeinfo;
	}

	public String getTypeinfo() {
		return this.typeinfo;
	}

	public void setTypeinfo(String typeinfo) {
		this.typeinfo = typeinfo;
	}
	
	@Override
	public void init() {
		id = 0;
		dateinfo = new Date(0);
		timeinfo = new Date(0);
		
		classinfo = typeinfo = reflink = "";
		message = priority = "";
	}

	@Override
	public void update(DataEntity entity) {
		FeedbackEntity newData = (FeedbackEntity) entity;
		id = newData.id;
		dateinfo = newData.dateinfo;
		timeinfo = newData.timeinfo;
		classinfo = newData.classinfo;
		typeinfo = newData.typeinfo;
		reflink = newData.reflink;
		message = newData.message;
		priority = newData.priority;
	}
	
	public static final String VAR_ID = "ID";
	public static final String VAR_DATE = "Date";
	public static final String VAR_TIME = "Time";
	public static final String VAR_CLASS = "Class";
	public static final String VAR_TYPE = "Type";
	public static final String VAR_REFLINK = "Reference";
	public static final String VAR_MESSAGE = "Message";
	public static final String VAR_PRIORITY = "Priority";

	@Override
	public Map<String, Object> getRecordProperties() {
		Map<String, Object> recordProperties = new LinkedHashMap<String, Object>();
		recordProperties.put(VAR_ID, id);
		recordProperties.put(VAR_DATE, dateinfo);
		recordProperties.put(VAR_TIME, timeinfo);
		recordProperties.put(VAR_CLASS, classinfo);
		recordProperties.put(VAR_TYPE, typeinfo);
		recordProperties.put(VAR_REFLINK, reflink);
		recordProperties.put(VAR_MESSAGE, message);
		recordProperties.put(VAR_PRIORITY, priority);
		return recordProperties;
	}

}