package com.example.demo.schedule.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@Service
public class AsyncApplication implements IAsyncApplication, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(AsyncApplication.class);

	@Value("${server.host}")
	private String host;

	@Value("${netty.server.port}")
	private Integer port;

	private AsyncConnector connector;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(()-> {
			ClientHandler clientHandler = new ClientHandler(this);
			this.connector = new AsyncConnector(clientHandler, host,port);
			connector.startConnector();
			logger.info("connect server success,host:{},port:{}", host, port);
		}).start();
	}

	@Override
	public void send(String message) {
		if(connector.send(message)) {
			logger.info("send success, message: {}",message);
		}else {
			logger.warn("send failed, message: {}",message);
		}
		
	}

	@Override
	public void onRead(String message) {
		System.out.println("message: " + message);
	}

}
