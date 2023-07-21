package com.nttdata.bootcamp.demo.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.core.crud.web.CrudValidator;

public class DemoValidator extends CrudValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Libro.class.isAssignableFrom(clazz);
		
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
