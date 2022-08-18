package com.example.demo.dataservice.application;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.rpc.protocol.dubbo.telnet.ChangeTelnetHandler;
import com.alibaba.fastjson.JSON;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// 不加组件扫描,默认扫描该类所在包
@ComponentScan("com.example")
@ImportResource("classpath:config/spring/spring-context.xml")
@EnableTransactionManagement
public class DemoApplication {

	public static void main(String[] args) {
//		String args1 = "dataServiceDubbo({'msgType':'111'})";
//		try {
//			List<Object> list = JSON.parse("["+args1+"]", List.class);
//			System.out.println(list);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		InvokeTelnetHandler
		SpringApplication.run(DemoApplication.class, args);
//		System.out.println(telnet(args1));
	}

	public static String telnet(String message) {
		if (message == null || message.length() == 0) {
			return "Please input method name, eg: \r\ninvoke xxxMethod(1234, \"abcd\", {\"prop\" : \"value\"})\r\ninvoke XxxService.xxxMethod(1234, \"abcd\", {\"prop\" : \"value\"})\r\ninvoke com.xxx.XxxService.xxxMethod(1234, \"abcd\", {\"prop\" : \"value\"})";
		}
		StringBuilder buf = new StringBuilder();
//		String service = (String) channel.getAttribute(ChangeTelnetHandler.SERVICE_KEY);
//		if (service != null && service.length() > 0) {
//			buf.append("Use default service " + service + ".\r\n");
//		}
		int i = message.indexOf("(");
		if (i < 0 || !message.endsWith(")")) {
			return "Invalid parameters, format: service.method(args)";
		}
		String method = message.substring(0, i).trim();
		String args = message.substring(i + 1, message.length() - 1).trim();
		i = method.lastIndexOf(".");
		if (i >= 0) {
//			service = method.substring(0, i).trim();
			method = method.substring(i + 1).trim();
		}
		List<Object> list;
		try {
			list = JSON.parseArray("[" + args + "]", Object.class);
		} catch (Throwable t) {
			return "Invalid json argument, cause: " + t.getMessage();
		}
		return null;
	}

}
