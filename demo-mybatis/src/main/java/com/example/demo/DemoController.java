package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

	@Autowired
	private TestService service;

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		int effectCount = service.add();
		return String.format("hello world,effectCount:%s",effectCount);
	}

}
