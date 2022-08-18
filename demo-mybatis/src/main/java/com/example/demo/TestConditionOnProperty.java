package com.example.demo;

import com.example.mybatis.model.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConditionOnProperty {

    @Bean
    @ConditionalOnProperty(value = {"xxx.flag"})
    public User user1(){
        System.out.println("-----------user-----------");
        return new User();
    }

    @Bean
    @ConditionalOnProperty(prefix = "xxx",name="flag",havingValue = "true")
    public User user2(){
        System.out.println("-----------user-----------");
        return new User();
    }

    @Bean
    @ConditionalOnProperty(name="xxx.flag")
    public User user3(){
        System.out.println("-----------user-----------");
        return new User();
    }

}
