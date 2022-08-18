package com.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类 AtomicInteger
 */
public class AtomicIntegerDemo {

    private AtomicInteger atomicInteger = new AtomicInteger();

    public AtomicInteger getValue() {
        return atomicInteger;
    }

    public static void main(String[] args) {
        AtomicIntegerDemo demo = new AtomicIntegerDemo();

        AtomicInteger atomicInteger = demo.getValue();
        System.out.println("初始值： " + atomicInteger); // int类型默认值为0

        System.out.println("AddAndGet: " + atomicInteger.addAndGet(10));  // 10

        System.out.println("GetAndAdd: " + atomicInteger.getAndAdd(5)); // 内存中已经变成15，但是返回更新前的值10

        System.out.println("after GetAndAdd: " + atomicInteger); // 15
		
    }


}
