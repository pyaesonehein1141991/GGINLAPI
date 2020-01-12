package org.tat.gginl.api.domains.repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.common.IDGen;

@Repository
public class IdGenRepositoryImpl implements IdGenRepository{

	@PersistenceContext
	private EntityManager em;

	public IdGenRepositoryImpl(EntityManager entityManager) {
		this.em = entityManager;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public IDGen getNextId(String generatedItem) {
		IDGen idGen = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem");
			Query selectQuery = em.createQuery(query.toString());
			selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			selectQuery.setHint("javax.persistence.lock.timeout", 30000);
			selectQuery.setParameter("generateItem", generatedItem);
			idGen = (IDGen) selectQuery.getSingleResult();
			idGen.setMaxValue(idGen.getMaxValue() + 1);
			idGen = em.merge(idGen);
			em.flush();
		} catch (PersistenceException e) {
			return null;
		} 
		return idGen;
	}

}