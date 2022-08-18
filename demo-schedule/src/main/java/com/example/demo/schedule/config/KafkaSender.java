package com.example.demo.schedule.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;

public class KafkaSender {
	
	@Value("${topic}")
	private String topic;
	
	private KafkaProducer<String,String> kafkaProducer;
	
	public KafkaSender(KafkaProducer<String,String> kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}
	
	public void send() {
		for (int i = 0; i < 5; i++) {
			String key =  Integer.toString(111);
			String value = Integer.toString(i);
			ProducerRecord<String,String> record =  new ProducerRecord<>(topic,key,value);
			kafkaProducer.send(record);
		}
		
	}
	
}
