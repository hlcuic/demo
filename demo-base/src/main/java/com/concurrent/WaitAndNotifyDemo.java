package com.concurrent;

public class WaitAndNotifyDemo {
	public static void main(String[] args) {
		WaitAndNotifyDemo demo = new WaitAndNotifyDemo();

		new Thread(() -> {
			synchronized (demo) {
				System.out.println(Thread.holdsLock(demo));
				try {
					System.out.println("before wait......");
					// wait的时候会把锁释放掉
					demo.wait();
					System.out.println("after wait......");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			synchronized (demo) {
				try {
					System.out.println(Thread.holdsLock(demo));
					// sleep的时候会放弃cpu的执行权，但是不会释放锁
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("before notify......");
				demo.notify();
				System.out.println("after notify......");
			}
		}).start();
		
		
	}
}
