package com.thisara.controller.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thisara.exception.ErrorCodes;
import com.thisara.exception.response.ValidationError;
import com.thisara.service.exception.ServiceException;
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
public class ExceptionController {

	private static final Logger logger = Logger.getLogger(ExceptionController.class.getName());

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ExceptionFormatter exceptionFormatter;

	@ExceptionHandler(value = { ServiceException.class })
	public ResponseEntity<ErrorResponse> handleException(ServiceException e) {
		logger.severe(e.getMessage());
		return exceptionFormatter.composeErrorResponse(e.getErrorCode(), e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ValidationException.class })
	public ResponseEntity<ErrorResponse> handleException(ValidationException e) {
		logger.severe(e.getMessage());
		return exceptionFormatter.composeErrorResponse(ErrorCodes.CODAT003, e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
		
		logger.severe(e.getMessage());
		
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		
		e.getConstraintViolations().forEach((error) -> {
			
			ValidationError validationError = new ValidationError();
			
			validationError.setCode(ErrorCodes.CODAT003.name());
			validationError.setDefaultMessage(error.getMessage());
			validationError.setField(error.getPropertyPath().toString());
			validationError.setRejectedValue(error.getInvalidValue().toString());
			
			validationErrors.add(validationError);
		});
		
		return exceptionFormatter.composeValidationErrorResponse(ErrorCodes.CODAT003,"Validation Failed",validationErrors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = BindException.class)
	public ResponseEntity<ErrorResponse> handleException(BindException e){
		logger.severe(e.getMessage());
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		
		e.getAllErrors().forEach((error) -> {
			ValidationError validationError = modelMapper.map((FieldError)error, ValidationError.class);
			validationErrors.add(validationError);
		});
		
		return exceptionFormatter.composeValidationErrorResponse(ErrorCodes.COBND001, "Validation Failed.", validationErrors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { NullPointerException.class })
	public ResponseEntity<ErrorResponse> handleException(NullPointerException e) {
		logger.severe(e.getMessage());
		return exceptionFormatter.composeErrorResponse(ErrorCodes.CODAT002, e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
	}
}
