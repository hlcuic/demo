package com.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class CustomLockTest {

	private int i = 0;

	public void increase() {
		i++;
	}

	public int getValue() {
		return i;
	}

	public static void main(String[] args) {
		CustomLockTest test = new CustomLockTest();
		ReentrantLock lock = new ReentrantLock();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				lock.lock();
				try {
					test.increase();
				} finally {
					lock.unlock();
				}
			}).start();
		}

		System.out.println("------tag1-----");
		while (Thread.activeCount() > 1) {
			System.out.println(Thread.activeCount());
			System.out.println(Thread.getAllStackTraces());
			Thread.yield();
		}
		System.out.println("------tag2-----");
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)+" ,值： "+test.getValue());

	}
}
