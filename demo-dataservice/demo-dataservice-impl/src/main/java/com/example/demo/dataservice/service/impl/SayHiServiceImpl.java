package com.example.demo.dataservice.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dataservice.annotation.MsgType;
import com.example.demo.dataservice.interfaces.IBussinessService;
import com.example.demo.dataservice.service.ISayHelloService;

@Service
public class SayHiServiceImpl implements ISayHelloService,IBussinessService{

	@Override
	@MsgType(request = "222")
	public String sayHello() {
		return "hello dataService 222!";
	}
	
}
