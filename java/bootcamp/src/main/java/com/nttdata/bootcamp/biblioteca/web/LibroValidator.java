package com.nttdata.bootcamp.biblioteca.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.core.crud.web.CrudValidator;

public class LibroValidator extends CrudValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Libro.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
