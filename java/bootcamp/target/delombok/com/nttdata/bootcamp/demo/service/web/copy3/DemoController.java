package com.nttdata.bootcamp.demo.service.web.copy3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import com.nttdata.bootcamp.demo.service.model.Demo;
import com.nttdata.bootcamp.demo.service.model.DemoPage;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

public class DemoController implements CrudController <Demo, DemoPage>{

	@Override
	public CrudService<Demo> getService() {
		// TODO Auto-generated method stub
		return null;
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