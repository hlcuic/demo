package com.example.demo.schedule.async;

public interface IAsyncApplication {

	void send(String message);

	void onRead(String message);

}
