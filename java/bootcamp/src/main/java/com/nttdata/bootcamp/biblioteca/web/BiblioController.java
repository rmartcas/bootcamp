package com.nttdata.bootcamp.biblioteca.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.model.LibroPage;
import com.nttdata.bootcamp.biblioteca.service.BiblioService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

@RestController
@RequestMapping("/biblio")
public class BiblioController implements CrudController<Libro, LibroPage> {
	
	@Autowired
	private BiblioService service;

	@Override
	public CrudService<Libro> getService() {
		return this.service;
	}

	@Override
	public Validator getValidator() {
		return new LibroValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return null;
	}
}
