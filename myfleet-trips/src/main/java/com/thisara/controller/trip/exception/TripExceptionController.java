package com.thisara.controller.trip.exception;

import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.thisara.controller.exception.ExceptionController;
import com.thisara.utils.ExceptionFormatter;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@ControllerAdvice("com.thisara.controller.trip")
public class TripExceptionController extends ExceptionController{

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ExceptionFormatter exceptionFormatter;

}
