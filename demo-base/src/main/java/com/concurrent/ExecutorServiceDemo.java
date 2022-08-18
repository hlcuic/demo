package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {
	public static void main(String[] args) {
//		testSingleThreadExecutor();
//		testFixedThreadExecutor();
		testCachedThreadExecutor();
	}
	
	public static void testCachedThreadExecutor() {
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			final int y = i;
			executors.execute(() -> {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "::" + y);
			});
		}
		executors.shutdown();
	}

	public static void testFixedThreadExecutor() {
		//  fixedThread线程如果因为运行问题挂掉，会重新再启动一个线程，放到线程池里
		ExecutorService executors = Executors.newFixedThreadPool(1);
		for (int i = -1; i < 10; i++) {
			final int y = i;
			executors.execute(() -> {
				System.out.println(Thread.currentThread().getName() + "::" + 5 / y);
			});
		}
		executors.shutdown();
	}

	public static void testSingleThreadExecutor() {
		// 单个线程池 singleThread线程如果因为运行问题挂掉，会重新再启动一个线程，放到线程池里
		ExecutorService executors = Executors.newSingleThreadExecutor();
		for (int i = -1; i < 10; i++) {
			final int y = i;
			executors.execute(() -> {
				System.out.println(Thread.currentThread().getName() + "::" + 5 / y);
			});
		}
		executors.shutdown();
	}

}
