package com.example.demo.dataservice.service.impl;

import com.example.demo.dataservice.service.IGenericService;

public class GenericServiceimpl implements IGenericService {

	@Override
	public String test(String params) {
		return "hello "+params+"!!!";
	}

}
