package com.concurrent;

import java.util.HashMap;
import java.util.Map;

public class CompareAndSwapDemo {
	public static void main(String[] args) {
		final CompareAnSwapDemo swap = new CompareAnSwapDemo();
		final Map<String, Integer> map = new HashMap<>();
		map.put("suc", 0);
		map.put("err", 0);
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int value = swap.getValue();
					boolean b = swap.compareAndSet(value, (int) (Math.random() * 101));
//                    System.out.println(b);
					// 这里计算成功与否只能做大致判断，由于获取，比较，赋值不是一个原子操作，有可能获取完值锁被其他线程占用了；
					if (b) {
						map.put("suc", map.get("suc") + 1);
					} else {
						map.put("err", map.get("err") + 1);
					}
				}
			}).start();
		}
		while (Thread.activeCount() > 2) { // 保证前面的线程都执行完
			Thread.yield();
		}
		System.out.println("成功：" + map.get("suc"));
		System.out.println("失败：" + map.get("err"));
	}
}

class CompareAnSwapDemo {
	private int value;

	// 需要一个获取当前值的同步方法
	public synchronized int getValue() {
		return value;
	}

	// 需要一个比较值的同步方法
	public synchronized boolean compare(int old) {
		return value == old;
	}

	// 需要一个赋值的同步方法
	public synchronized boolean compareAndSet(int old, int newValue) {
		if (!compare(old))
			return false;
		this.value = newValue;
		return true;
	}
}
