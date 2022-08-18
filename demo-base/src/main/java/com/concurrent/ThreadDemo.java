package com.concurrent;

public class ThreadDemo {
	public static void main(String[] args) {
		Thread.currentThread();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
