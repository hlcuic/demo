package com.example.demo.schedule.job;

import com.example.demo.schedule.util.AppContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.demo.dataservice.api.IDataServiceDubbos;
import com.example.demo.schedule.config.KafkaSender;

@Component
public class TestSimpleJob implements SimpleJob {

	private Logger logger = LoggerFactory.getLogger(TestSimpleJob.class);

	@Autowired
	private IDataServiceDubbos dataService;
	
	@Autowired
	private KafkaSender kafkaSender;
	
//	@Autowired
//	private IAsyncApplication application;

	@Override
	public void execute(ShardingContext shardingContext) {
		String jobParam = shardingContext.getJobParameter();
		logger.info("-----start TestSimpleJob-----" + "\n" + jobParam);
//		testDubboInvoke();
//		testGenericInvoke();
		testSendKafka();
//		testNettyInvoke();
	}
	
	private void testNettyInvoke() {
//		application.send("hello nettyServer!!!");
	}

	// 测试dubbo调用
	private void testDubboInvoke() {
		String result = dataService.dataServiceDubbo("111");
		System.out.println(result);
	}

	// 测试泛化调用
	private void testGenericInvoke() {
		GenericService genericService = AppContextHolder.getBean("genericService");
		Object result = genericService.$invoke("test", new String[] { "java.lang.String" }, new Object[] { "generic" });
		System.out.println(result);
	}
	
	// 测试kafka异步调用
	private void testSendKafka() {
		kafkaSender.send();
	}

}
