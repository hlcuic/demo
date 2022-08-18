package com.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {
	public static void main(String[] args) {
		List<String> list = new CopyOnWriteArrayList<>();
		list.add("1");
		
		Object[] arr = new Object[2];
		System.out.println(arr.length);
		arr[0] = new Object();
		arr[1] = new Object();
		System.out.println(Arrays.toString(arr));
		
	}
}
