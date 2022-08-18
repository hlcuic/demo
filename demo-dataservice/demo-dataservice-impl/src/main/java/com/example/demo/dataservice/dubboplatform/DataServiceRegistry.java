package com.example.demo.dataservice.dubboplatform;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dataservice.annotation.MsgType;
import com.example.demo.dataservice.interfaces.IBussinessService;

@Service
public class DataServiceRegistry {
	
	private Logger logger = LoggerFactory.getLogger(DataServiceRegistry.class);
	
	private Map<String,Invoker> invokeMap = new ConcurrentHashMap<>();
	
	@Autowired
	private List<IBussinessService> services;
	
//	@PostConstruct
	private void init() {
		for(IBussinessService service:services) {
			 Class<?> clazz = service.getClass();
			 Method[] methods = clazz.getDeclaredMethods();
			 for(Method method:methods) {
				MsgType msgType =  method.getDeclaredAnnotation(MsgType.class);
				if(null==msgType) {
					continue;
				}
				
				String requestType = msgType.request();
				if(!invokeMap.containsKey(requestType)) {
					Invoker invoker = new Invoker();
					invokeMap.put(requestType, invoker);
				}
				Invoker invoker = invokeMap.get(requestType);
				invoker.setBean(service);
				invoker.setMethod(method);
				logger.info("registry service finished,requestMsgType:{}",requestType);
			 }
			 
		}
		
	}
	
	public String invoke(String msgType) {
		Invoker invoker = invokeMap.get(msgType);
		if(null!=invoker) {
			Object value = invoker.invoke();
			return value!=null?value.toString():null;
		}else {
			String warnMessage = "request msgType: "+ msgType + " not registry";
			logger.warn(warnMessage);
			return warnMessage;
		}
		
	}
	
	class Invoker{
		private Object bean;
		private Method method;
		
		public Invoker() {}
		
		public void setBean(Object bean) {
			this.bean = bean;
		}

		public void setMethod(Method method) {
			this.method = method;
		}
		
		public Object invoke() {
			try {
				return method.invoke(bean,new Object[] {});
			} catch (Exception e) {
				logger.error("",e);
				return null;
			}
		}
		
		
	}
	
}
