package com.thisara.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Car;
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
public class CarDAOImpl implements CarDAO {

	private static Logger logger = Logger.getLogger(CarDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Car> search(String registrationNumber) throws DAOException {

		List<Car> carsList = null;

		try {

			TypedQuery<Car> query = entityManager
					.createQuery("FROM Car c WHERE c.registrationNumber LIKE :registrationNumber ", Car.class);

			query.setParameter("registrationNumber", registrationNumber + "%");

			carsList = query.getResultList();

		} catch (QueryTimeoutException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Query timeout exception", ErrorCodes.DADAO001);
		} catch (DataException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Data exception", ErrorCodes.DADAT002);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}

		return carsList;
	}

	@Override
	public Car getCar(String registrationNumber) throws DAOException {

		Car car = null;

		try {

			TypedQuery<Car> query = entityManager.createQuery(
					"SELECT c FROM Car c, Tyre t WHERE c.id = t.car.id AND c.registrationNumber = :registrationNumber ",
					Car.class);

			query.setParameter("registrationNumber", registrationNumber);
			
			car = (Car) query.getSingleResult();

		} catch (NoResultException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Entity not found for : " + registrationNumber, ErrorCodes.DADAT001);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}

		return car;
	}

	@Override
	public Car getReference(Long id) throws DAOException {
		Car car = null;

		try {

			car = entityManager.getReference(Car.class, id);

		} catch (EntityNotFoundException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Entity not found", ErrorCodes.DADAT001);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}

		return car;
	}

	@Override
	public void save(Car car) throws DAOException {

		try {

			entityManager.persist(car);

		} catch (DataIntegrityViolationException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Constraint violation", ErrorCodes.DADAT002);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}
	}

	@Override
	public void update(Car car) throws DAOException {
		// TODO - to be developed
	}

	@Override
	public void remove(Car car) throws DAOException {

		try {
			
			entityManager.remove(car);

		} catch (EntityNotFoundException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Entity not found", ErrorCodes.DADAT001);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}
	}

}
