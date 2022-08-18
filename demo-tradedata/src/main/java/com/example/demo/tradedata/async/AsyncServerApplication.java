package com.example.demo.tradedata.async;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AsyncServerApplication implements IAsyncApplication{
	
	private Logger logger = LoggerFactory.getLogger(AsyncServerApplication.class);
	
	@Value("${netty.server.port}")
	private Integer port;
	
	private AsyncServerService asyncServer;
	
	private ServerHandler serverHandler;

	@PostConstruct
	private void init() {
		serverHandler = new ServerHandler(this);
		asyncServer = new AsyncServerService(serverHandler,port);
		asyncServer.startBind();
		logger.info("start server successfully,port: {}",port);
	}

	@Override
	public void onRead(String message) {
		System.out.println(message);
		send("i am server,thank you");
	}

	@Override
	public boolean send(String msg) {
		return asyncServer.send(msg);
	}
	
	

}
