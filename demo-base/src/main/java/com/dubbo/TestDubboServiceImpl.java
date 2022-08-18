package com.dubbo;

public class TestDubboServiceImpl implements  ITestDubboService{
    @Override
    public String testInvoke(String param) {
        System.out.println("调用服务端.......");
        return "hello,"+param;
    }
}
