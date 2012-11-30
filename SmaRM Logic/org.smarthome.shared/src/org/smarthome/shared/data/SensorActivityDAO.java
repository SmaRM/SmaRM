package org.smarthome.shared.data;

import org.smarthome.shared.connectors.PostgresDAO;
import org.smarthome.shared.datamodel.DataEntity;
import org.smarthome.shared.datamodel.SensorActivityEntity;

public class SensorActivityDAO extends PostgresDAO<SensorActivityEntity> {
	static SensorActivityDAO instance;
	
	public SensorActivityDAO(Class<? extends DataEntity> typeParameterClass, String persistenceUnitName) {
		super(typeParameterClass, persistenceUnitName);
	}

	public static SensorActivityDAO dao() {
		if (instance == null) {
			instance = new SensorActivityDAO(SensorActivityEntity.class, "ActivityDataPU");
		}
		return instance;
	}
}
