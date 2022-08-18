package com.example.demo.manager.config;

import java.util.Properties;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConfig {

	@Value("${bootstrap.servers}")
	private String bootstrapServers;
	
	@Value("${group.id}")
	private String groupId;
	
	@Value("${enable.auto.commit}")
	private String enableAutoCommit;
	
	@Value("${auto.commit.interval.ms}")
	private String autoCommitInterval;

	@Bean
	public KafkaConsumer<String, String> kafkaConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("group.id", groupId);
		props.put("enable.auto.commit",enableAutoCommit);
        props.put("auto.commit.interval.ms",autoCommitInterval);
		return new KafkaConsumer<>(props,new StringDeserializer(), new StringDeserializer());
	}

	@Bean
	public KafkaReceiver kafkaReceiver() {
		return new KafkaReceiver(kafkaConsumer());
	}

}
