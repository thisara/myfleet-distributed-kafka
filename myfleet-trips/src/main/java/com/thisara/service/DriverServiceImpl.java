package com.thisara.service;

import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisara.controller.dto.driver.PostDriverDTO;
import com.thisara.dao.DriverDAO;
import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Driver;
import com.thisara.service.exception.ServiceException;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Service
public class DriverServiceImpl implements DriverService{

	private static final Logger logger = Logger.getLogger(DriverServiceImpl.class.getName());
	
	@Autowired
	DriverDAO driverDAO;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	@Transactional
	public void save(PostDriverDTO postDriverDTO) throws ServiceException {
		
		try {
		
			Driver driver = modelMapper.map(postDriverDTO, Driver.class);
		
			driverDAO.save(driver, postDriverDTO.getUser());
		
		}catch (DAOException e) {
			logger.severe(e.getMessage());
			throw new ServiceException(e.getMessage(), e.getErrorCode());
		}
	}
}
