package com.thisara.dao;

import com.thisara.dao.exception.DAOException;
import com.thisara.domain.Driver;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface DriverDAO {

	public Driver getReference(Long id) throws DAOException;

	void save(Driver driver, String userId) throws DAOException;
}
