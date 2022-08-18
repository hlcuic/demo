package com.concurrent;

public class JoinDemo {
	public static void main(String[] args) {
		
		Thread t1 = new Thread(()->{
			System.out.println("------"+Thread.currentThread().getName()+"------");
		},"thread-t1");
		
		Thread t2 = new Thread(()->{
			try {
				Thread.sleep(1000L);
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("------"+Thread.currentThread().getName()+"------");
		},"thread-t2");
		
		t1.start();
		t2.start();
		
		
		try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName());
	}
}
