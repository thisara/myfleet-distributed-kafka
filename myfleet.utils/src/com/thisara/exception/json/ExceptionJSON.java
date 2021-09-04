package com.thisara.exception.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ExceptionJSON {

	public ObjectNode createExceptionJson(String errorId, String methodSignature, String apiResponse, String stackTrace, String serverInfo) {

		ObjectMapper objectMapper = new ObjectMapper();

		ObjectNode exceptionLog = objectMapper.createObjectNode();

		exceptionLog.put("error-id", errorId);
		exceptionLog.put("server-info", serverInfo);
		
		ObjectNode exceptionDetail = objectMapper.createObjectNode();

		exceptionDetail.put("method-signature", methodSignature);
		exceptionDetail.put("api-response", apiResponse);
		exceptionDetail.put("stacktrace", stackTrace);

		exceptionLog.set("exception-detail", exceptionDetail);

		return exceptionLog;
	}
}
