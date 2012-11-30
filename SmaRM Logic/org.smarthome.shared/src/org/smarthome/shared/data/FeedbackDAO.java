package org.smarthome.shared.data;

import org.smarthome.shared.connectors.PostgresDAO;
import org.smarthome.shared.datamodel.DataEntity;
import org.smarthome.shared.datamodel.FeedbackEntity;

public class FeedbackDAO extends PostgresDAO<FeedbackEntity> {
	static FeedbackDAO instance;
	
	public FeedbackDAO(Class<? extends DataEntity> typeParameterClass, String persistenceUnitName) {
		super(typeParameterClass, persistenceUnitName);
	}

	public static FeedbackDAO dao() {
		if (instance == null) {
			instance = new FeedbackDAO(FeedbackEntity.class, "ActivityDataPU");
		}
		return instance;
	}
}
