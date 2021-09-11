package com.thisara.exception.handler;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thisara.exception.messenger.ExceptionMessenger;
import com.thisara.utils.response.ErrorResponse;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Aspect
@Component
public class ExceptionLogger {
	
	Logger logger = Logger.getLogger(ExceptionLogger.class.getName());
	
	@Autowired
	public ExceptionMessenger exceptionMessenger;
	
	@SuppressWarnings("unchecked")
	@Around("ExceptionPointCuts.exceptionHandller()")
	public Object logException(ProceedingJoinPoint joinPoint) throws Throwable{

		Object proceed = joinPoint.proceed();
		
		Object stackTrace = joinPoint.getArgs()[0];
		Signature signature = joinPoint.getSignature();
		ResponseEntity<ErrorResponse> apiResponse = (ResponseEntity<ErrorResponse>)proceed;
		String errorId = apiResponse.getBody().getErrorid();
		
		ObjectNode exceptionLog = createExceptionJson(errorId, signature, apiResponse, stackTrace);
		
		logger.info(exceptionLog.toPrettyString());

		exceptionMessenger.publishMessage(errorId,exceptionLog.toPrettyString());
	
		return proceed;
	}
	
	public ObjectNode createExceptionJson(String errorId, Signature signature, ResponseEntity<ErrorResponse> apiResponse, Object stackTrace) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ObjectNode exceptionLog = objectMapper.createObjectNode();
		
		exceptionLog.put("error-id", errorId);
		
		ObjectNode exceptionDetail = objectMapper.createObjectNode();
		
		exceptionDetail.put("signature", signature.toString());
		exceptionDetail.put("api-response", apiResponse.toString());
		exceptionDetail.put("stacktrace", stackTrace.toString());
		
		exceptionLog.set("exception-detail", exceptionDetail);
		
		return exceptionLog;
	}
}
