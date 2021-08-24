package com.thisara.dao;

import java.util.List;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Trip;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface TripDAO {

	public List<Trip> search(String fromDate, String fromTime, String carRegistrationNumber) throws DAOException;
	
	public List<Trip> get(String carRegistrationNumber) throws DAOException;
	
	public void save(Trip trip, String userId) throws DAOException;
}
