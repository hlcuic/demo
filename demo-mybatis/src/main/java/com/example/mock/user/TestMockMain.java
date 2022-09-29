package com.example.mock.user;

import com.example.mock.spring.HelloApplicationContext;

/**
 * @author cuihailong
 * @date 2022/9/29 14:00
 */
public class TestMockMain {
    public static void main(String[] args) {
        HelloApplicationContext context = new HelloApplicationContext(AppConfig.class);
        MockService mockService = (MockService)context.getBean("mockService");
        mockService.doSomething();
    }
}
