package com.thisara.exception.handler;

import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
@ControllerAdvice
public class ExceptionAdvice {

	private static final Logger logger = Logger.getLogger(ExceptionAdvice.class.getName());

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private ExceptionFormatter exceptionFormatter;
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		logger.severe(e.getMessage());
		return exceptionFormatter.composeErrorResponse(ErrorCodes.GEN000, "General Error", HttpStatus.BAD_GATEWAY);
	}
}
