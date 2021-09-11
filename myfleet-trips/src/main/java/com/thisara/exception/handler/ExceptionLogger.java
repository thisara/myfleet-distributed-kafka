package com.thisara.exception.handler;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thisara.exception.messenger.ExceptionMessenger;
import com.thisara.myfleet.ExceptionJSONFactory;
import com.thisara.service.TripServiceImpl;
import com.thisara.utils.response.ErrorResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
	
	private Logger logger = LoggerFactory.getLogger(ExceptionLogger.class);
	
	@Autowired
	public ExceptionJSONFactory exceptionJsonFactory;
	
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

		JSONObject serverInfo = getServerInfo();
		
		ObjectNode exceptionLog = exceptionJsonFactory.getExceptionJson().createExceptionJson(errorId, signature.toString(), apiResponse.toString(), stackTrace.toString(), serverInfo.toString());
		
		logger.error(exceptionLog.toPrettyString());
		
		exceptionMessenger.publishMessage(errorId,exceptionLog.toPrettyString());
		
		//messagePublisher(errorId,exceptionLog.toPrettyString());
	
		return proceed;
	}
	
	public JSONObject getServerInfo() throws UnknownHostException{
		
		InetAddress ip = InetAddress.getLocalHost();
        String hostname = ip.getHostName();;
		
        JSONObject serverInfo = new JSONObject();
		
		serverInfo.put("ip", ip);
		serverInfo.put("hostname", hostname);
		
		return serverInfo;
	}
	
}
