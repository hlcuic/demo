package com.example.demo.schedule.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
// 没有组件扫描注解时 @ComponentScan("com.example") ,默认扫描该类所在包以及子包
@ComponentScan("com.example")
@ImportResource("classpath:config/spring/spring-context.xml")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
