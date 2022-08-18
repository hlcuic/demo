package com.concurrent;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

	public static void main(String[] args) {

		// 同步队列：不存储具体的数据，如果生产者线程put后就一直阻塞，等待消费线程take走数据
		final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

		Thread t1 = new Thread(() -> {

			System.out.println("-----before put-----");
			try {
				queue.put(111);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-----after put-----");
		}, "thread-t1");

		Thread t2 = new Thread(() -> {

			System.out.println("-----before take-----");
			try {
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-----after take-----");
		}, "thread-t2");

		t1.start();
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();

	}
}
