package com.thisara.utils.response;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class ErrorResponse {

	private String errorid;
	private String errorcode;
	private String message;
	private String status;
	private String timestamp;

	public ErrorResponse() {
		
	}
	
	public ErrorResponse(String errorid, String errorcode, String message, String status, String timestamp) {
		this.errorid = errorid;
		this.errorcode = errorcode;
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public String getErrorid() {
		return errorid;
	}

	public void setErrorid(String errorid) {
		this.errorid = errorid;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorid=" + errorid + ", errorcode=" + errorcode + ", message=" + message + ", status="
				+ status + ", timestamp=" + timestamp + "]";
	}
}
