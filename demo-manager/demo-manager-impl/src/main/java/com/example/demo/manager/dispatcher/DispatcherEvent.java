package com.example.demo.manager.dispatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.manager.define.HandlerType;
import com.example.demo.manager.define.TransModel;
import com.example.demo.manager.interfaces.IEventHandler;

@Service
public class DispatcherEvent {

	private Logger logger = LoggerFactory.getLogger(DispatcherEvent.class);

	private Map<HandlerType, IAsynListener> listenerMap = new ConcurrentHashMap<>();

	@Autowired
	private List<IEventHandler> list;

	@PostConstruct
	private void init() {
		if (!CollectionUtils.isEmpty(list)) {
			for (IEventHandler handler : list) {
				HandlerType type = handler.getHandlerType();
				if (!listenerMap.containsKey(type)) {
					IAsynListener listener = new MessageAsynListener();
					listenerMap.put(type, listener);
				}
				IAsynListener listener = listenerMap.get(type);
				listener.registry(handler);
			}
		} else {
			logger.warn("event list is empty");
		}

	}

	public void sendEvent(TransModel model) {
		logger.info("dispatcher msgType: {}",model.getMsgType());
		String msgType = model.getMsgType();
		for (Map.Entry<HandlerType, IAsynListener> entry : listenerMap.entrySet()) {
			IAsynListener listener = entry.getValue();
			if (listener.accpet(msgType)) {
				listener.onEvent(model);
				break;
			}
		}

	}

}
