package com.thisara.service;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.thisara.exception.ErrorCodes;
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
@Service
public class CarServiceImpl implements CarService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${car.service.url}")
	private String localCarsServiceUrl;
	
	@Override
	public CarDTO getCar(String carRegistrationNumber) throws ServiceException {
		
		String carResourceUrl = localCarsServiceUrl+"/"+carRegistrationNumber;

		try {
		
			@NotNull CarDTO carDTO = restTemplate.getForObject(carResourceUrl, CarDTO.class);
			
			return carDTO;
			
		}catch(HttpClientErrorException e) {
			throw new ServiceException(e.getLocalizedMessage(), ErrorCodes.SEDAT001);
		}
	}
}
