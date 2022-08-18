package com.example.demo.schedule.job;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping("/sayHi")
	@ResponseBody
	public String sayHi() {
		return "say hi!";
	}

}
