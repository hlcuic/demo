package com.example.demo.manager.handler;

import org.springframework.stereotype.Service;

import com.example.demo.manager.define.HandlerType;
import com.example.demo.manager.define.MsgType;
import com.example.demo.manager.interfaces.IEventHandler;

@Service
public class PersistMessageHandler implements IEventHandler{
	
	@MsgType(request="111")
	public void persistMessage(String params) {
//		System.out.println(params);
	}

	@Override
	public HandlerType getHandlerType() {
		return HandlerType.serial;
	}
	
}
