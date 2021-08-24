package com.thisara.service;

import java.util.List;

import com.thisara.controller.dto.car.PostCarDTO;
import com.thisara.domain.Car;
import com.thisara.service.exception.ServiceException;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface CarService {

	public List<Car> search(String registrationNumber)throws ServiceException;
	
	public Car getCar(String registrationNumber) throws ServiceException;
	
	public void save(PostCarDTO postCarDTO) throws ServiceException;
	
	public void update(Car car) throws ServiceException;
	
	public void remove(String carId) throws ServiceException;
}
