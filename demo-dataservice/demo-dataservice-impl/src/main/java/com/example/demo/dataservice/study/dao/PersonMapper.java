package com.example.demo.dataservice.study.dao;

import java.util.List;
import java.util.Map;

public interface PersonMapper {
	List<Map<String, String>> selectAll();
	int delete();
	int insert();
}
