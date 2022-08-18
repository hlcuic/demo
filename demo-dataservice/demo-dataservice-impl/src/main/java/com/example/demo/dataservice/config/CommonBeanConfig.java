package com.example.demo.dataservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.dataservice.service.IGenericService;
import com.example.demo.dataservice.service.impl.GenericServiceimpl;

@Configuration
public class CommonBeanConfig {
	
	@Bean
	public IGenericService  genericServiceimpl() {
		return new GenericServiceimpl();
	}
	
}
