package org.smarthome.shared.connectors;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.smarthome.shared.datamodel.DataEntity;

public abstract class PostgresDAO<T extends DataEntity> {
	
	private final Class<? extends DataEntity> typeParameterClass;
	private DataEntityManager entityManager;
	
	public PostgresDAO(Class<? extends DataEntity> typeParameterClass, String persistenceUnitName) {
		entityManager = new DataEntityManager(persistenceUnitName);
		this.typeParameterClass = typeParameterClass;
	}
	
	@SuppressWarnings("unchecked")
	public T getData(int id) {
		 return (T) entityManager.find(typeParameterClass, id);
	}

	public List<T> getData() {
		String queryStmt = "SELECT x FROM " + typeParameterClass.getName() + " x";
		List<T> results = entityManager.query(queryStmt);
		return results;
	}
	
	public List<T> getDataWithLimit(int limit) {
		String queryStmt = "SELECT x FROM " + typeParameterClass.getName() + " x";
		List<T> results =  entityManager.queryWithLimit(queryStmt, limit);
		return results;
	}

	public Integer getMaximumValue(String columnName) {
		String queryStmt = "SELECT MAX(x.?1) FROM ?2 x";
		Integer result = entityManager.querySingle(queryStmt, columnName, typeParameterClass.getName());
		return result;
	}

	public List<T> getData(String columnName, Date start, Date end, TemporalType tempType) {
		String queryStmt = "SELECT x FROM ";
		queryStmt += typeParameterClass.getName() + " x ";
		queryStmt += "WHERE x." + columnName + " >= \'?1\' ";
		queryStmt += "AND x." + columnName + " <= \'?2\'";
		
		Query query = entityManager.createQuery(queryStmt);
		query.setParameter(1, start, tempType);
		query.setParameter(2, end, tempType);
		
		List<T> results = entityManager.query(query);
		return results;
	}

	public void addData(DataEntity entity) {
		entityManager.persist(entity);
	}

	public void updateData(DataEntity entity) {
		entityManager.update(entity);
	}
			
	public void removeData(DataEntity entity) {
		entityManager.remove(entity);
	}

	public void removeData(int id) {
		entityManager.remove(id);
	}
}
