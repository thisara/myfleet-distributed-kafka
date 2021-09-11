package com.thisara.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisara.controller.dto.trip.PostTripDTO;
import com.thisara.dao.DriverDAO;
import com.thisara.dao.TripDAO;
import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Driver;
import com.thisara.domain.Trip;
import com.thisara.exception.ErrorCodes;
import com.thisara.service.car.dto.CarDTO;
import com.thisara.service.exception.ServiceException;
import com.thisara.utils.datetime.DateTimeParser;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Service
public class TripServiceImpl implements TripService{
	
	private Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);
	
	@Autowired
	private TripDAO tripDAO;
	
	@Autowired
	private DriverDAO driverDAO;
	
	@Autowired
	private CarService carService;
	
	@Override
	public List<Trip> search(String fromDate, String fromTime, String carRegistrationNumber) throws ServiceException{
		
		List<Trip> trips = null;
				
		try {
			logger.info("searching for trip from : " + fromDate + " for registration number : " + carRegistrationNumber) ;
			
			trips =	tripDAO.search(fromDate, fromTime, carRegistrationNumber);
		
		} catch (DAOException e) {
			logger.error("Error searching for trip : " + e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}
		
		return trips;
	}

	@Override
	public List<Trip> get(String carRegistrationNumber) throws ServiceException {
		
		List<Trip> trips = null;
		
		try {
			logger.info("get trips of car registration number : " + carRegistrationNumber) ;
			
			trips = tripDAO.get(carRegistrationNumber);
		
		} catch (DAOException e) {
			logger.error("Error getting trips : " + e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}
		
		return trips;
	}

	@Override
	@Transactional(rollbackOn = ServiceException.class)
	public void save(PostTripDTO postTripDTO) throws ServiceException {
		
		try {
			
			Trip trip = new Trip();
			
			Driver driver = driverDAO.getReference(postTripDTO.getDriverId());
			
			CarDTO carDTO = carService.getCar(postTripDTO.getCarRegistrationNumber());
			
			if(postTripDTO.getCarRegistrationNumber().equals(carDTO.getRegistrationNumber())) {
			
				trip.setCarRegistrationNumber(postTripDTO.getCarRegistrationNumber());
				trip.setDriver(driver);
				trip.setDistance(postTripDTO.getDistance());
				trip.setNumber(postTripDTO.getTripNumber());
				
				trip.setTripStartTimezone(postTripDTO.getTripStartTimezone());
				trip.setTripEndTimezone(postTripDTO.getTripEndTimezone());
				
				trip.setTripStartTimestamp(DateTimeParser.parseOffsetTimestamp(postTripDTO.getTripStartTimestamp(), postTripDTO.getTripStartTimezone()));
				trip.setTripEndTimestamp(DateTimeParser.parseOffsetTimestamp(postTripDTO.getTripEndTimestamp(), postTripDTO.getTripEndTimezone()));
				
				logger.info("Saving trip for : " + trip.getCarRegistrationNumber());
				
				tripDAO.save(trip, postTripDTO.getUser());
				
				logger.info("Trip saved for : " + trip.getCarRegistrationNumber());
		
			}else {
				
				ErrorCodes dataInconsistencyErrorCode = ErrorCodes.SEDAT002;
				
				logger.error("Data inconsistency error saving trip for car : " + trip.getCarRegistrationNumber() + "| Error code : " + dataInconsistencyErrorCode);
				
				throw new ServiceException("Data Inconsistency Error!", dataInconsistencyErrorCode);
			}
		}catch (DAOException e) {
			logger.error("Error saving trip : " + e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}catch (ServiceException e) {
			logger.error("Error saving trip : " + e.getMessage());
			throw e;
		}
	}
}
