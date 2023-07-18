package com.nttdata.bootcamp.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.bootcamp.demo.model.DemoPage;
import com.nttdata.bootcamp.demo.service.DemoService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

@RestController
@RequestMapping("/demo")
public class DemoController implements CrudController<Demo, DemoPage> {
	
	@Autowired
	private DemoService service;

	@Override
	public CrudService<Demo> getService() {
		return this.service;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator getSearchValidator() {
		// TODO Auto-generated method stub
		return null;
	}

}
