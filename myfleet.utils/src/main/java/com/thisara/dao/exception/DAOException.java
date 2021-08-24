package com.thisara.dao.exception;

import com.thisara.exception.ErrorCodes;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorCodes errorCode;
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message, ErrorCodes errorCode) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public ErrorCodes getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodes errorCode) {
		this.errorCode = errorCode;
	}
}
