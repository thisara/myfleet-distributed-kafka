package com.thisara.service;

import com.thisara.controller.dto.driver.PostDriverDTO;
import com.thisara.service.exception.ServiceException;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface DriverService {

	public void save(PostDriverDTO postDriverDTO) throws ServiceException;
}
