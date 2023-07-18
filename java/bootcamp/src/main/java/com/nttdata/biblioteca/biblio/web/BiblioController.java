package com.nttdata.biblioteca.biblio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.biblioteca.biblio.mapper.BiblioService;
import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.bootcamp.demo.model.DemoPage;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

@RestController
@RequestMapping("/biblio")
public class BiblioController implements CrudController<Demo, DemoPage> {

	@Autowired
	private BiblioService service;
	@Override
	public CrudService<Demo> getService() {
		// TODO Auto-generated method stub
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
