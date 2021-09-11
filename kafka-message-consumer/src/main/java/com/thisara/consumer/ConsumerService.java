package com.thisara.consumer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thisara.connectors.JavaMongoConnect;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-08-30
 */
@RestController
@RequestMapping("/consume")
public class ConsumerService {

	private Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	Environment env;

	@Autowired
	private RebalanceListener rebalanceListener;

	@GetMapping("/message")
	public ResponseEntity<String> getMessage() throws Exception {

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

		// TODO - Constructor Injection
		rebalanceListener.setKafkaConsumer(consumer);

		final int allowedEmptyAttempts = Integer.parseInt(env.getProperty("consumer.poll.attempts.empty.max"));
		int emptyAttempts = 0;

		LocalDateTime dateTimeNow = LocalDateTime.now();

		try {

			while (true) {

				final ConsumerRecords<String, String> consumerRecords = consumer
						.poll(Duration.ofMillis(Long.parseLong(env.getProperty("consumer.poll.interval"))));

				try {

					if (consumerRecords.count() == 0) {
						emptyAttempts++;
						if (emptyAttempts > allowedEmptyAttempts)
							break;
						else
							continue;
					}

					consumerRecords.forEach(record -> {

						String topic = record.topic();
						int partition = record.partition();
						long offset = record.offset();

						JSONObject topicInfo = getTopicInfo(topic, partition, offset);

						boolean publishStatus = JavaMongoConnect.getJavaMongoConnect().saveMessage(record.key(),
								record.value(), dateTimeNow, topic, topicInfo);

						logger.info(record.key() + " Publish status : " + publishStatus);

						rebalanceListener.addOffset(topic, partition, offset);
					});

				} catch (Exception e) {
					logger.error("Error reading message : " + e.getLocalizedMessage());
				}
			}

		} catch (Exception e) {
			logger.error("Error reading messages! " + e.getLocalizedMessage());
		} finally {
			consumer.commitSync();
			consumer.close();
			logger.info("Consumer closed!");
		}
	}

	public JSONObject getTopicInfo(String topicName, int partitionNumber, long offset) {

		JSONObject topicInfo = new JSONObject();

		topicInfo.put("topic", topicName);
		topicInfo.put("partition", partitionNumber);
		topicInfo.put("offset", offset);

		return topicInfo;
	}
}
