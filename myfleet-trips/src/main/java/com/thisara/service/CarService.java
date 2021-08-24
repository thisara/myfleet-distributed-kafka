package com.thisara.service;

import com.thisara.service.car.dto.CarDTO;
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

	public CarDTO getCar(String carRegistrationNumber) throws ServiceException;
}
