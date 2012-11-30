package org.smarthome.data.connectors;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.smarthome.shared.datamodel.DataEntity;

public class DataEntityManager {
	
	private EntityManagerFactory entitiyManagerFactory;

	private EntityManager entitiyManager;

	private boolean activeSession;

	public DataEntityManager(String persistenceProvider) {
		entitiyManagerFactory = Persistence.createEntityManagerFactory(persistenceProvider);
		
		activeSession = false;
	}

	private EntityManager getEntityManager() {
		if (entitiyManager == null || !entitiyManager.isOpen()) {
			entitiyManager = entitiyManagerFactory.createEntityManager();
		}
		return entitiyManager;
	}

	private void closeEntityManager() {
		if (entitiyManager != null && !activeSession) {
			entitiyManager.close();
		}
	}

	private Query buildQuery(String queryStmt, Object... queryParams) {
		Query query = createQuery(queryStmt);
		int paramIdx = 1;
		for (Object param : queryParams) {
			query.setParameter(paramIdx, param);
			paramIdx++;
		}
		return query;
	}

	public Query createQuery(String queryStmt) {
		return getEntityManager().createQuery(queryStmt);
	}

	public <T> T find(Class<T> type, int id) {
		T result = null;
		try {
			result = getEntityManager().find(type, id);
		} finally {
			closeEntityManager();
		}
		return result;
	}

	public <T> List<T> query(String queryStmt, Object... queryParams) {
		Query query = buildQuery(queryStmt, queryParams);
		return query(query, queryParams);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(Query query, Object... queryParams) {
		List<T> results = null;
		try {
			results = query.getResultList();
		} finally {
			closeEntityManager();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public <T> T querySingle(String queryStmt, Object... queryParams) {
		Query query = buildQuery(queryStmt, queryParams);
		Object result = null;
		try {
			result = query.getSingleResult();
		} finally {
			closeEntityManager();
		}
		return (T) result;
	}

	public void persist(DataEntity entity) {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().persist(entity);
			getEntityManager().getTransaction().commit();
		} finally {
			closeEntityManager();
		}
	}

	public void update(DataEntity entity) {
		activeSession = true;
		DataEntity objToUpdate = find(entity.getClass(), entity.getId());
		try {
			getEntityManager().getTransaction().begin();
			objToUpdate.update(entity);
			getEntityManager().getTransaction().commit();
		} finally {
			closeEntityManager();
			activeSession = false;
		}
	}

	public void remove(DataEntity entity) {
		DataEntity objToRemove = find(entity.getClass(), entity.getId());
		remove(objToRemove.getId());
	}

	public void remove(int id) {
		activeSession = true;
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().remove(id);
			getEntityManager().getTransaction().commit();
		} finally {
			closeEntityManager();
			activeSession = false;
		}
	}
}
