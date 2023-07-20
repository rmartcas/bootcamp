package com.nttdata.biblioteca.biblio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.biblioteca.biblio.model.Libro;
import com.nttdata.biblioteca.biblio.model.LibroPage;
import com.nttdata.biblioteca.biblio.service.BiblioService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

@RestController
@RequestMapping("/biblioteca")
public class BiblioController implements CrudController<Libro, LibroPage> {

	@Autowired
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
