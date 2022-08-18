package com.concurrent;

import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
	public static void main(String[] args) throws Exception {
		String result = "hello";
		FutureTask<String> futureTask = new FutureTask<>(() -> {
			System.out.println(result);
		},result);

		new Thread(futureTask).start();

		System.out.println(futureTask.get());

	}
}
