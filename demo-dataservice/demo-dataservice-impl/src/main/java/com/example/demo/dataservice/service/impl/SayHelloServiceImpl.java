package com.example.demo.dataservice.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dataservice.annotation.MsgType;
import com.example.demo.dataservice.interfaces.IBussinessService;
import com.example.demo.dataservice.service.ISayHelloService;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class SayHelloServiceImpl implements ISayHelloService,IBussinessService{

	@Override
	@MsgType(request = "111")
	public String sayHello() throws UnknownHostException {
//		try {
//			Thread.sleep(5000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		InetAddress inetAddress = InetAddress.getLocalHost();
		String ip = inetAddress.getHostAddress();
		System.out.println("ip: "+ip);
		return "hello dataService! "+ ip;
	}

	public static void main(String[] args) throws UnknownHostException {
		InetAddress inetAddress = InetAddress.getLocalHost();
		String ip = inetAddress.getHostAddress();
		System.out.println(ip);
	}
	
}
