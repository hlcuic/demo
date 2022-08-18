package com.example.demo.schedule.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestService implements ApplicationRunner,CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		System.out.println(args);
		System.out.println("-------服务启动成功-------");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(args);
		System.out.println("-------服务启动成功-------");
	}

}
