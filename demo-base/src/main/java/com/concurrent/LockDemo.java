package com.concurrent;

public class LockDemo {
	public static void main(String[] args) {
		LockDemo demo = new LockDemo();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (demo) {
					try {
						System.out.println(Thread.currentThread().getName()+":"
								+Thread.holdsLock(demo));
						// sleep的时候只是放弃了cpu的执行权，放弃时间片，但是仍然持有锁
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		},"thread-1").start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (demo) {
					System.out.println(Thread.currentThread().getName());
				}
			}
			
		},"thread-2").start();
		
		System.out.println(Thread.currentThread().getName());
		
	}
}
