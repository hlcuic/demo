package com.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import scala.reflect.runtime.SynchronizedOps;

public class DubboConsumerService{
    public static void main(String[] args) {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-application");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("10.20.130.230:9090");
        registry.setUsername("aaa");
        registry.setPassword("bbb");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<ITestDubboService> reference = new ReferenceConfig<>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
//        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(ITestDubboService.class);
        // 直连方式，调用服务端接口
        reference.setUrl("dubbo://localhost:20880");
        reference.setVersion("1.0.0");

        // 和本地bean一样使用xxxService
        ITestDubboService dubboService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        String response = dubboService.testInvoke("小明");
        System.out.println("response:"+response);
        synchronized (DubboConsumerService.class){
            try {
                DubboConsumerService.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
