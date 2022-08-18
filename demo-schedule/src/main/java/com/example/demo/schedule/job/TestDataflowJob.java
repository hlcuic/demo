package com.example.demo.schedule.job;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

@Component
public class TestDataflowJob implements DataflowJob<String>{
	
	private Logger logger = LoggerFactory.getLogger(TestDataflowJob.class);

	@Override
	public List<String> fetchData(ShardingContext shardingContext) {
		logger.info("---start fetch data---");
		List<String> list = new ArrayList<>();
		String params = shardingContext.getJobParameter();
		list.add(params);
		return list;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<String> data) {
		logger.info("---start process data---");
		System.out.println(data);
	}

}
