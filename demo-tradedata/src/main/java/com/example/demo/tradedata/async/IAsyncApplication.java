package com.example.demo.tradedata.async;

public interface IAsyncApplication {

	void onRead(String msg);

	boolean send(String msg);
}
