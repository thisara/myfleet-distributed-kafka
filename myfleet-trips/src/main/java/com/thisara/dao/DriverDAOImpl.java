package com.thisara.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Driver;
import com.thisara.exception.ErrorCodes;
import com.thisara.utils.audit.RecordAuditor;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Repository
public class DriverDAOImpl implements DriverDAO {

	private Logger logger = LoggerFactory.getLogger(DriverDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Driver getReference(Long id) throws DAOException {

		Driver driver = null;

		try {

			driver = entityManager.getReference(Driver.class, id);

		} catch (EntityNotFoundException e) {
			logger.error(e.getMessage());
			throw new DAOException("Entity not found", ErrorCodes.DADAT001);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}

		return driver;
	}
	
	@Override
	public void save(Driver driver, String userId) throws DAOException{
		
		try {
			
			driver = (Driver)RecordAuditor.createAuditFields(driver, userId);			
			
			entityManager.persist(driver);
			
		} catch (ConstraintViolationException e) {
			logger.error(e.getMessage());
			throw new DAOException("Constraint violation", ErrorCodes.DADAT002);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}
		
	}
}
