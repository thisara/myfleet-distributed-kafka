package com.thisara.dao;

import java.util.List;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Car;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface CarDAO {

	public List<Car> search(String registrationNumber) throws DAOException;
	
	public Car getCar(String registrationNumber) throws DAOException;
	
	public Car getReference(Long id) throws DAOException;
	
	public void save(Car car) throws DAOException;
	
	public void update(Car car) throws DAOException;
	
	public void remove(Car car) throws DAOException;
	
}
