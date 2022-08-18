package com.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
	public static void main(String[] args) {
		// ConcurrentHashMap是并发线程安全的map，jdk1.8采用的是在node节点上加锁的方式，是锁的粒度更细，
		// 区别于jdk1.7的分段锁
		Map<String,String> map = new ConcurrentHashMap<>();
		map.put("1","aaa");
		map.get("1");
	}
	
	
}
