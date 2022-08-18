package com.example.demo.manager.interfaces;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IHandlerMessage {
	void handler(ConsumerRecord<String,String> record);
}
