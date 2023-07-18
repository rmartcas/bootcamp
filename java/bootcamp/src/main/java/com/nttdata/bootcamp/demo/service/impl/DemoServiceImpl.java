package com.nttdata.bootcamp.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.demo.mapper.DemoMapper;
import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.bootcamp.demo.service.DemoService;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class DemoServiceImpl implements DemoService{

	@Autowired
	private DemoMapper mapper;

	@Override
	public CrudMapper<Demo> getMapper() {
		return mapper;
	}
	
}
