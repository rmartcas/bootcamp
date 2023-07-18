package com.nttdata.bootcamp.biblioteca.autor.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.core.crud.web.CrudValidator;

public class AutorValidator extends CrudValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Autor.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
