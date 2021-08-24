package com.thisara.service;

import java.util.List;

import com.thisara.controller.dto.trip.PostTripDTO;
import com.thisara.domain.Trip;
import com.thisara.service.exception.ServiceException;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface TripService {

	public List<Trip> search(String fromDate, String fromTime, String carRegistrationNumber) throws ServiceException;
	
	public List<Trip> get(String carRegistrationNumber) throws ServiceException;
	
	public void save(PostTripDTO postTripDTO) throws ServiceException;
}
