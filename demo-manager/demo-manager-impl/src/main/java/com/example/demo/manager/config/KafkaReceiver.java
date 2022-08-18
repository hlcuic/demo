package com.example.demo.manager.config;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.manager.interfaces.IHandlerMessage;

public class KafkaReceiver implements Runnable{
	
	@Value("${kafka.consume.timeout}")
	private Long timeout = 5000L;
	
	@Value("${kafka.consume.topics}")
	private String topics;
	
	@Autowired
	private List<IHandlerMessage> consumerList;
	
	private KafkaConsumer<String,String> kafkaConsumer;
	
	public KafkaReceiver(KafkaConsumer<String,String> kafkaConsumer) {
		this.kafkaConsumer = kafkaConsumer;
	}
	
	@PostConstruct
	private void init() {
		this.kafkaConsumer.subscribe(Arrays.asList(topics.split(",")));
		new Thread(this).start();
	}
	
	@SuppressWarnings("deprecation")
	public void receive() {
		while(true) {
			ConsumerRecords<String,String> records = kafkaConsumer.poll(timeout);
			Iterator<ConsumerRecord<String,String>> iter = records.iterator();
			while(iter.hasNext()) {
				ConsumerRecord<String,String> record = iter.next();
				System.out.println(record);
				for(IHandlerMessage consumer:consumerList) {
					consumer.handler(record);
				}
			}
		}
		
	}

	@Override
	public void run() {
		receive();
	}
	
	
}
