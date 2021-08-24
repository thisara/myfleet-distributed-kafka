package com.thisara.message.consumer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consume")
public class ConsumerService {
    
    Logger logger = Logger.getLogger(ConsumerService.class.getName());
    
    @Autowired
    Environment env;
	
	@GetMapping("/message")
	public ResponseEntity<String> getMessage() throws Exception{

		runConsumer();

		return new ResponseEntity<String>("Consumer Started!", HttpStatus.OK);
	}
	
	private KafkaConsumer<String, String> createConsumer() {
		
	      Properties properties = new Properties();

	      properties.put("group.id", env.getProperty("consumer.group.id"));
	      properties.put("bootstrap.servers", env.getProperty("consumer.bootstrap.servers"));
	      properties.put("key.deserializer", env.getProperty("consumer.key.deserializer"));
		  properties.put("value.deserializer", env.getProperty("consumer.value.deserializer"));

	      KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

	      consumer.subscribe(Collections.singletonList(env.getProperty("consumer.topic.name")));
	      
	      return consumer;
	}
	
	private void runConsumer() throws InterruptedException {
		
        KafkaConsumer<String, String> consumer = createConsumer();

        final int allowedEmptyAttempts = Integer.parseInt(env.getProperty("consumer.poll.attempts.empty.max"));
        int emptyAttempts = 0;
        
        LocalDateTime dateTimeNow = LocalDateTime.now();
        
        while (true) {
        	
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(Long.parseLong(env.getProperty("consumer.poll.interval"))));
            
            try {
            
            	if (consumerRecords.count() == 0) {
            		emptyAttempts++;
            			if (emptyAttempts > allowedEmptyAttempts) break;
            			else continue;
            	}
            
	            consumerRecords.forEach(record -> {
	               
	               JSONObject topicInfo = getTopicInfo(record.topic(),record.partition(),record.offset());
	               
	               boolean publishStatus = JavaMongoConnect.getJavaMongoConnect().saveMessage(record.key(), record.value(), dateTimeNow, record.topic(), topicInfo);
	               
	               logger.info(record.key() + " Publish status : " + publishStatus);
	            });
            
            consumer.commitAsync();
            
            }catch(Exception e) {
            	logger.severe(e.getLocalizedMessage());
            	e.printStackTrace();
            }
        }
        
        consumer.close();
        
        System.out.println("DONE");
    }
	
	public JSONObject getTopicInfo(String topicName, int partitionNumber, long offset) {
		
		JSONObject topicInfo = new JSONObject();
		
		topicInfo.put("topic", topicName);
		topicInfo.put("partition", partitionNumber);
		topicInfo.put("offset", offset);
		
		return topicInfo;
	}
}
