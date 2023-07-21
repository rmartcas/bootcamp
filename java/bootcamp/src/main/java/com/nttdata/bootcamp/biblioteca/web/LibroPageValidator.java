package com.nttdata.bootcamp.biblioteca.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.biblioteca.model.LibroPage;
import com.nttdata.core.crud.web.CrudValidator;

public class LibroPageValidator extends CrudValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return LibroPage.class.isAssignableFrom(clazz);
		
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}