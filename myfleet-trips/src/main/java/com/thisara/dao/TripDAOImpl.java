package com.thisara.dao;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Driver;
import com.thisara.domain.Trip;
import com.thisara.exception.ErrorCodes;
import com.thisara.utils.audit.RecordAuditor;
import com.thisara.utils.datetime.DateTimeParser;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Repository
public class TripDAOImpl implements TripDAO {

	private static final Logger logger = Logger.getLogger(TripDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<Trip> search(String fromDate, String fromTime, String carRegistrationNumber) throws DAOException {

		List<Trip> tripList = new ArrayList<Trip>();

		try {

			Query query = entityManager.createQuery("SELECT t, d FROM Trip t "
					+ "INNER JOIN t.driver d "
					+ "WHERE t.tripStartTimestamp > :searchDateTime AND t.carRegistrationNumber = :carRegistrationNumber");

			query.setParameter("searchDateTime", DateTimeParser.parseDateTime(fromDate, fromTime, 0));
			query.setParameter("carRegistrationNumber", carRegistrationNumber);

			List<Object[]> list = query.getResultList();

			for (Object[] arr : list) {
				Trip trip = (Trip) (arr)[0];
				trip.setDriver((Driver) (arr)[1]);
				tripList.add(trip);
			}

		} catch (QueryTimeoutException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Query timeout exception", ErrorCodes.DADAO001);
		} catch (DataException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Data exception", ErrorCodes.DADAT002);
		}catch (DateTimeParseException e) {
			logger.severe(e.getMessage());
			throw new DAOException("DateTime parse exception", ErrorCodes.DADAT002);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}

		return tripList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Trip> get(String carRegistrationNumber) throws DAOException {

		List<Trip> tripList = new ArrayList<Trip>();

		try {

			Query query = entityManager.createQuery("select t, d from Trip t " + "INNER JOIN t.driver d "
					+ "where t.carRegistrationNumber = :registrationNumber");

			query.setParameter("registrationNumber", carRegistrationNumber);

			List<Object[]> list = query.getResultList();

			for (Object[] arr : list) {
				Trip trip = (Trip) (arr)[0];
				trip.setDriver((Driver) (arr)[1]);
				tripList.add(trip);
			}

		} catch (QueryTimeoutException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Query timeout exception", ErrorCodes.DADAT001);
		} catch (DataException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Data error", ErrorCodes.DADAT002);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}

		return tripList;
	}

	@Override
	public void save(Trip trip, String userId) throws DAOException {

		try {

			trip = (Trip)RecordAuditor.createAuditFields(trip, userId);
			
			entityManager.persist(trip);

		} catch (ConstraintViolationException e) {
			logger.severe(e.getMessage());
			throw new DAOException("Constraint violation", ErrorCodes.DADAT002);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new DAOException("General exception", ErrorCodes.DAGEN001);
		}
	}
}
