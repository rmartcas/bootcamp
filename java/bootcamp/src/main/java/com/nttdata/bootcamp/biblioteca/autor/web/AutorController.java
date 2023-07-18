package com.nttdata.bootcamp.biblioteca.autor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.bootcamp.biblioteca.autor.model.AutorPage;
import com.nttdata.bootcamp.biblioteca.autor.service.AutorService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;


@RestController
@RequestMapping("/autor")
public class AutorController implements CrudController<Autor, AutorPage>{
	
	@Autowired
	private AutorService service;

	@Override
	public CrudService<Autor> getService() {
		// TODO Auto-generated method stub
		return service;
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
