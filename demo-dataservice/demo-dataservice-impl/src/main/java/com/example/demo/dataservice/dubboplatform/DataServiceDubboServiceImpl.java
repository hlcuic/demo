package com.example.demo.dataservice.dubboplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dataservice.api.IDataServiceDubbos;

@Service
public class DataServiceDubboServiceImpl implements IDataServiceDubbos {
	
	private Logger logger = LoggerFactory.getLogger(DataServiceDubboServiceImpl.class);

	@Autowired
	private DataServiceRegistry dataServiceRegistry;

	@Override
	public String dataServiceDubbo(String msgType) {
		logger.info("invoke method msgType:{}",msgType);
		return dataServiceRegistry.invoke(msgType);
	}
	
}
