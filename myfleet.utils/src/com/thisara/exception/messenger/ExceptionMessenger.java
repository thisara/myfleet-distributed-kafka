package com.thisara.exception.messenger;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.core.env.Environment;

@Repository
public class ExceptionMessenger {

	@Autowired
    private Environment env;
	
	private Logger logger = LoggerFactory.getLogger(ExceptionMessenger.class);
	
	public void publishMessage(String key, String message) {
		
		String topic = env.getProperty("kafka.application.error.topic");//"myfleet_user_error_log";
		
		Properties properties = new Properties();

		properties.put("bootstrap.servers", env.getProperty("kafka.bootstrap.servers"));

		properties.put("acks", env.getProperty("kafka.acks"));

		properties.put("retries", env.getProperty("kafka.retries"));

		properties.put("batch.size", env.getProperty("kafka.batch.size"));

		properties.put("linger.ms", env.getProperty("kafka.linger.ms"));
		
		properties.put("buffer.memory", env.getProperty("kafka.buffer.memory"));
		
		properties.put("key.serializer", env.getProperty("kafka.key.serializer"));
		
		properties.put("value.serializer", env.getProperty("kafka.value.serializer"));
		
		try {
			
			Producer<String, String> producer = new KafkaProducer<String, String>(properties);
			
			producer.send(new ProducerRecord<String, String>(topic,key, message));
			
			logger.info("Message sent successfully");
		
			producer.close();
		
		}catch(Exception e) {
			logger.error("Log publisher failed. " + e.getLocalizedMessage());
		}
	}
}
