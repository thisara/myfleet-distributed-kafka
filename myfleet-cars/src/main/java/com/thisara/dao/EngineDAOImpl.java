package com.thisara.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Engine;
import com.thisara.exception.ErrorCodes;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Repository
public class EngineDAOImpl implements EngineDAO {

	private static final Logger logger = Logger.getLogger(EngineDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Engine engine) throws DAOException {

		try {

			entityManager.persist(engine);

		} catch (ConstraintViolationException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Constraint violation exception", ErrorCodes.DADAT002);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}
	}
}
