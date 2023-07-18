package com.nttdata.bootcamp.biblioteca.libro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.biblioteca.libro.model.Libro;
import com.nttdata.bootcamp.biblioteca.libro.model.LibroPage;
import com.nttdata.bootcamp.biblioteca.libro.service.LibroService;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;


@RestController
@RequestMapping("/libro")
public class LibroController implements CrudController<Libro, LibroPage>{
	
	@Autowired
	private LibroService service;

	@Override
	public CrudService<Libro> getService() {
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
