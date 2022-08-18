package com.example.demo.schedule.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {
	
	@Value("${bootstrap.servers}")
	private String bootstrapServers;
	
	@Bean
	public KafkaProducer<String,String> kafkaProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers",bootstrapServers);
		props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
		return new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
	}
	
	@Bean
	public KafkaSender kafkaSender() {
		return new KafkaSender(kafkaProducer());
	}
	

}
