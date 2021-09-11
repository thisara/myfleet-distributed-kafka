package com.thisara.consumer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-08-30
 */
@Component
public class RebalanceListener implements ConsumerRebalanceListener{
	
	Logger logger = LoggerFactory.getLogger(RebalanceListener.class);
	
	private KafkaConsumer<String, String> kafkaConsumer;

	private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<TopicPartition, OffsetAndMetadata>();

	public Map<TopicPartition, OffsetAndMetadata> getCurrentOffsets() {
		return currentOffsets;
	}
	
	public void addOffset(String topic, int partition, long offset) {
		currentOffsets.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset, "commit"));
	}

	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		logger.info("Committing offset due to consumer rebalancing!");
		kafkaConsumer.commitSync(currentOffsets);
		currentOffsets.clear();
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		logger.info("Reading new partitions after rebalancing!");
	}
	//TODO - Inject through constructor
	public void setKafkaConsumer(KafkaConsumer<String, String> kafkaConsumer) {
		this.kafkaConsumer = kafkaConsumer;
	}

}
