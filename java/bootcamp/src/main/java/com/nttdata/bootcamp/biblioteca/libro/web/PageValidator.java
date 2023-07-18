package com.nttdata.bootcamp.biblioteca.libro.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.biblioteca.autor.model.AutorPage;
import com.nttdata.core.crud.web.CrudValidator;

public class PageValidator extends CrudValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return AutorPage.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
