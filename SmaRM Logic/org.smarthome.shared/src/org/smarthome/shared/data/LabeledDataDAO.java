package org.smarthome.shared.data;

import org.smarthome.shared.PersistenceProvider;
import org.smarthome.shared.connectors.PostgresDAO;
import org.smarthome.shared.datamodel.DataEntity;
import org.smarthome.shared.datamodel.LabeledDataEntity;


public class LabeledDataDAO extends PostgresDAO<LabeledDataEntity> {
	static LabeledDataDAO instance;
	
	public LabeledDataDAO(Class<? extends DataEntity> typeParameterClass, String persistenceUnitName) {
		super(typeParameterClass, persistenceUnitName);
	}

	public static LabeledDataDAO dao() {
		if (instance == null) {
			instance = new LabeledDataDAO(LabeledDataEntity.class, PersistenceProvider.NAME);
		}
		return instance;
	}
}