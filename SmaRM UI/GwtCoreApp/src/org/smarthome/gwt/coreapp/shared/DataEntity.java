package org.smarthome.gwt.coreapp.shared;

import java.io.Serializable;
import java.util.Map;

import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

public interface DataEntity extends JsonSerializable, Serializable {
	void init();
	
	Integer getId();
	void setId(Integer id);
	
	void update(DataEntity entity);	
	
	Map<String, Object> getRecordProperties();
}
