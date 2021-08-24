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
	
	/*
	//Remove
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
	
	public void messagePublisher(String key, String message) {
		
		String topic = "myfleet_error_log";
		
		Properties properties = new Properties();
		
		//Assign localhost id
		properties.put("bootstrap.servers", "192.168.1.7:9092,192.168.1.8:9092");
		
		//Set acknowledgements for producer requests. 
		properties.put("acks", "all");
		
		//If the request fails, the producer can automatically retry,
		properties.put("retries", 5);
		
		//Specify buffer size in config
		properties.put("batch.size", 16384);
		
		//Reduce the no of requests less than 0  
		properties.put("linger.ms", 1);
		
		//The buffer.memory controls the total amount of memory available to the producer for buffering.   
		properties.put("buffer.memory", 33554432);
		
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		try {
			
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		producer.send(new ProducerRecord<String, String>(topic,key, message));
		
		System.out.println("Message sent successfully");
		
		producer.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
}
