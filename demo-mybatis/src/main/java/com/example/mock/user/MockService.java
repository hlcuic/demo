package com.example.mock.user;

import com.example.mock.spring.HelloComponent;

/**
 * @author cuihailong
 * @date 2022/9/29 14:09
 */
@HelloComponent("mockService")
public class MockService {

    public void doSomething(){
        System.out.println("mockService doSomething....");
    }
}
