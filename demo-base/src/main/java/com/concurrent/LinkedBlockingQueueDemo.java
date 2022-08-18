package com.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueDemo {
	public static void main(String[] args) {
		
		// 单线程无界队列，维护一个核心线程，最大线程为1
		ThreadPoolExecutor executors = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
		
		for(int i=0;i<100;i++) {
			final int y = i;
			executors.execute(()->{
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(y+"  对列长度："+executors.getQueue().size());
			});
		}
		System.out.println("put finished-----");
		executors.shutdown();
	}
}
