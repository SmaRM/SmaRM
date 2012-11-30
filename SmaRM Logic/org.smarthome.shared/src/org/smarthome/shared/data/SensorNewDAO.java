package org.smarthome.shared.data;

import org.smarthome.shared.connectors.PostgresDAO;
import org.smarthome.shared.datamodel.DataEntity;
import org.smarthome.shared.datamodel.SensorNewEntity;

public class SensorNewDAO extends PostgresDAO<SensorNewEntity> {
	static SensorNewDAO instance;
	
	public SensorNewDAO(Class<? extends DataEntity> typeParameterClass, String persistenceUnitName) {
		super(typeParameterClass, persistenceUnitName);
	}

	public static SensorNewDAO dao() {
		if (instance == null) {
			instance = new SensorNewDAO(SensorNewEntity.class, "ActivityDataPU");
		}
		return instance;
	}
}
