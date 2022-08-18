package com.example.demo.manager.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.manager.define.TransModel;
import com.example.demo.manager.dispatcher.DispatcherEvent;
import com.example.demo.manager.interfaces.IHandlerMessage;

@Service
public class DispatchMessageService implements IHandlerMessage{
	
	@Autowired
	private DispatcherEvent dispatcher;

	@Override
	public void handler(ConsumerRecord<String,String> record) {
		String msgType = record.key();
		String value = record.value();
		TransModel model = new TransModel(msgType,value);
		dispatcher.sendEvent(model);
	}
	
}
