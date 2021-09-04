package com.thisara.exception.messenger;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.core.env.Environment;

@Repository
public class ExceptionMessenger {

	@Autowired
    private Environment env;
	
	public void publishMessage(String key, String message) {
		
		String topic = "myfleet_user_error_log";
		
		Properties properties = new Properties();
		
		//Assign localhost id
		properties.put("bootstrap.servers", env.getProperty("kafka.bootstrap.servers"));
		
		//Set acknowledgements for producer requests. 
		properties.put("acks", env.getProperty("kafka.acks"));
		
		//If the request fails, the producer can automatically retry,
		properties.put("retries", env.getProperty("kafka.retries"));
		
		//Specify buffer size in config
		properties.put("batch.size", env.getProperty("kafka.batch.size"));
		
		//Reduce the no of requests less than 0  
		properties.put("linger.ms", env.getProperty("kafka.linger.ms"));
		
		//The buffer.memory controls the total amount of memory available to the producer for buffering.   
		properties.put("buffer.memory", env.getProperty("kafka.buffer.memory"));
		
		properties.put("key.serializer", env.getProperty("kafka.key.serializer"));
		
		properties.put("value.serializer", env.getProperty("kafka.value.serializer"));
		
		try {
			
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		producer.send(new ProducerRecord<String, String>(topic,key, message));
		
		System.out.println("Message sent successfully");
		
		producer.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
