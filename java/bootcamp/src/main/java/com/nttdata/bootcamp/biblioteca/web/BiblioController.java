package com.nttdata.bootcamp.biblioteca.web;

import org.springframework.validation.Validator;

import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.model.LibroPage;
import com.nttdata.bootcamp.biblioteca.service.BiblioService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

public class BiblioController implements CrudController<Libro, LibroPage>{
	
	private BiblioService service;

	@Override
	public CrudService<Libro> getService() {
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
