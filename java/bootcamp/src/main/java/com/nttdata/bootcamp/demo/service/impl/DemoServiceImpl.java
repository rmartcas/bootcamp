package com.nttdata.bootcamp.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.demo.mapper.DemoMapper;
import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.bootcamp.demo.service.DemoService;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	private DemoMapper mapper;

	@Override
	public CrudMapper<Demo> getMapper() {
		return this.mapper;
	}

	@Override
	public void helloService(Demo demo) {
		this.mapper.hello(demo);
		String hello1 = demo.getAtributo1();
		hello1 = hello1 + " , Hello from service!";
		demo.setAtributo1(hello1);
	}
}
