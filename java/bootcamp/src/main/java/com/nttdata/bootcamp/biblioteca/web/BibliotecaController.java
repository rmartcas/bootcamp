package com.nttdata.bootcamp.biblioteca.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.model.LibroPage;
import com.nttdata.bootcamp.biblioteca.service.BibliotecaService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

@RestController
@RequestMapping("/bliblioteca")
public class BibliotecaController implements CrudController<Libro, LibroPage>{

	@Autowired
	private BibliotecaService service;
	
	@Autowired
	private LibroValidator validator;
	
	@Override
	public CrudService<Libro> getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return validator;
	}

	@Override
	public Validator getSearchValidator() {
		// TODO Auto-generated method stub
		return null;
	}

}
