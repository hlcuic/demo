package com.example.demo.manager.dispatcher;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.manager.define.MsgType;
import com.example.demo.manager.define.TransModel;
import com.example.demo.manager.interfaces.IEventHandler;

public class EventCracker {
	
	private Logger logger = LoggerFactory.getLogger(EventCracker.class);

	private Map<String, Invoker> invokeMap = new ConcurrentHashMap<>();

	public void register(IEventHandler handler) {
		Class<?> clazz = handler.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			MsgType msgType = method.getDeclaredAnnotation(MsgType.class);
			if (null == msgType) {
				continue;
			}

			String requestType = msgType.request();

			if (!invokeMap.containsKey(requestType)) {
				Invoker invoker = new Invoker();
				invokeMap.put(requestType, invoker);
			}
			Invoker invoker = invokeMap.get(requestType);
			invoker.setBean(handler);
			invoker.setMethod(method);
		}

	}
	
	public boolean contains(String msgType) {
		return invokeMap.containsKey(msgType);
	}

	public Object invoke(TransModel model) throws Exception {
		String msgType = model.getMsgType();
		Object[] args = new Object[] {model.getValue()};
		logger.info("eventCrack msgType: {}",msgType);
		Invoker invoker = invokeMap.get(msgType);
		if (invoker != null) {
			return invoker.invoke(args);
		}
		return null;
	}

	private class Invoker {
		private Object bean;
		private Method method;

		public void setBean(Object bean) {
			this.bean = bean;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public Object invoke(Object[] args) throws Exception {
			return method.invoke(bean, args);
		}

	}

}
