package com.thisara.exception.response;

import java.util.List;

import com.thisara.utils.response.ErrorResponse;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class ValidationErrorResponse extends ErrorResponse{

	private List<ValidationError> errors;
	
	public ValidationErrorResponse(String errorid, String errorcode, String message, List<ValidationError> errors, String status, String timestamp) {
		super(errorid, errorcode, message, status, timestamp);
		this.errors = errors;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
}
