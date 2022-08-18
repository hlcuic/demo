package com.concurrent;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {

	// 保证内存可见性与禁止指令重排序
	private volatile boolean flag = true;
	
	public void run() {
		while (flag) {
			System.out.println("---111---");
		}
		System.out.println("---finished---");
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public static void main(String[] args) {
		VolatileDemo demo = new VolatileDemo();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(5L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("---设置flag值---");
				demo.setFlag(false);
			}

		}).start();
		demo.run();
		
	}

}
