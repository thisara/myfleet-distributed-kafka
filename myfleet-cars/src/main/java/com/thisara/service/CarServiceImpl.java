package com.thisara.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thisara.controller.dto.car.PostCarDTO;
import com.thisara.dao.CarDAO;
import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Car;
import com.thisara.domain.Engine;
import com.thisara.domain.Tyre;
import com.thisara.exception.ErrorCodes;
import com.thisara.service.exception.ServiceException;
import com.thisara.utils.audit.RecordAuditor;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Service
public class CarServiceImpl implements CarService {

	private Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
	
	@Autowired
	private CarDAO carDAO;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Car getCar(String registrationNumber) throws ServiceException {
		Car car = null;

		try {

			car = carDAO.getCar(registrationNumber);

		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}

		return car;
	}

	@Override
	public List<Car> search(String registrationNumber) throws ServiceException {

		List<Car> cars = null;

		try {

			cars = carDAO.search(registrationNumber);

		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}

		return cars;
	}

	@Override
	@Transactional(rollbackOn = ServiceException.class)
	public void save(PostCarDTO postCarDTO) throws ServiceException {
		
		try {
			
			Car car = modelMapper.map(postCarDTO, Car.class);
			
			car = (Car)RecordAuditor.createAuditFields(car, postCarDTO.getUser());
			
			Engine engine = modelMapper.map(postCarDTO, Engine.class);

			engine = (Engine)RecordAuditor.createAuditFields(engine, postCarDTO.getUser());

			car.addEngine(engine);

			List<Tyre> tyres = postCarDTO.getTyreData().stream().map(source -> modelMapper.map(source, Tyre.class))
								.collect(Collectors.toList());

			for (Tyre tyre : tyres) {

				tyre.setCar(car);
				
				tyre = (Tyre)RecordAuditor.createAuditFields(tyre, postCarDTO.getUser());
				
				car.addTyre(tyre);
			}

			carDAO.save(car);
			
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), ErrorCodes.SEGEN002);
		}
	}

	@Override
	public void update(Car car) throws ServiceException {

		try {

			carDAO.update(car);

		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}
	}

	@Override
	@Transactional(rollbackOn = ServiceException.class)
	public void remove(String carId) throws ServiceException {

		Car car = null;

		try {

			car = carDAO.getReference(Long.parseLong(carId));

			carDAO.remove(car);

		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}
	}
}
