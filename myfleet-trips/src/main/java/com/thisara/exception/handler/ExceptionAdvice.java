package com.thisara.exception.handler;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private ExceptionFormatter exceptionFormatter;
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		logger.error(e.getMessage());
		return exceptionFormatter.composeErrorResponse(ErrorCodes.GEN000, "General Error", HttpStatus.BAD_GATEWAY);
	}
}
