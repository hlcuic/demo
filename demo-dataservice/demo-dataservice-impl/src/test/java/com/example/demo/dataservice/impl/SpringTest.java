package com.example.demo.dataservice.impl;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-demo.xml");
        System.out.println(context.getBean("demo"));
        System.out.println(context.getBean("demo1"));
    }

}
