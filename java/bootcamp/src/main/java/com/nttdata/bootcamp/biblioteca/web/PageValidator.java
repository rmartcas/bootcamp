package com.nttdata.bootcamp.biblioteca.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.biblioteca.model.PageLibro;
import com.nttdata.core.crud.web.CrudValidator;

public class PageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return PageLibro.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}

