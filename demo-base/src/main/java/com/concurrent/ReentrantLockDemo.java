package com.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try {
			
		} finally {
			lock.unlock();
		}
	}
}
