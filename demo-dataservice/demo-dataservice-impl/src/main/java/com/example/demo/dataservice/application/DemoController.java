package com.example.demo.dataservice.application;

import com.example.demo.dataservice.mysql.TestMysqlPerformance;
import com.example.demo.dataservice.mysql.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dataservice.api.IDataServiceDubbos;

@Controller
public class DemoController {
	
	@Autowired
	private IDataServiceDubbos dubboServices;
	
	@Autowired
	private TestMysqlPerformance testMysqlPerformance;
	
	@RequestMapping("/sayhello")
	@ResponseBody
	public String sayHello(String msgType) {
		return dubboServices.dataServiceDubbo(msgType);
	}
	
	/**
	 * 1: 插入1万条数据，耗时
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(){
		testMysqlPerformance.insert_1w();
		return "inserting......";
	}
	
}
