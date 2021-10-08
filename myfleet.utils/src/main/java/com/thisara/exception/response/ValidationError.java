package com.thisara.exception.response;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class ValidationError {

	private String code;
	private String defaultMessage;
	private String field;
	private String rejectedValue;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getRejectedValue() {
		return rejectedValue;
	}
	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
}
