package com.thisara.controller.car.exception;

import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thisara.controller.exception.ExceptionController;
import com.thisara.exception.ErrorCodes;
import com.thisara.utils.ExceptionFormatter;
import com.thisara.utils.response.ErrorResponse;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@ControllerAdvice("com.thisara.controller.car")
public class CarExceptionController extends ExceptionController{

	private static final Logger logger = Logger.getLogger(CarExceptionController.class.getName());
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ExceptionFormatter exceptionFormatter;

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException e) {
		logger.severe(e.getMessage());
		return exceptionFormatter.composeErrorResponse(ErrorCodes.CODAT002, "Record already exist", HttpStatus.BAD_REQUEST);
	}
}
