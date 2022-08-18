package com.example.demo.schedule.service;

import com.example.demo.dataservice.api.IDataServiceDubbos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

	@Autowired
	private IDataServiceDubbos dataServiceDubbos;
	
	@RequestMapping("/sayhello")
	@ResponseBody
	public String sayHello() {
		System.out.println("hello world!");
		return dataServiceDubbos.dataServiceDubbo("111");
	}
	
}
