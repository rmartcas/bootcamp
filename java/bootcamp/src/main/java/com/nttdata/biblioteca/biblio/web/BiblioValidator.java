package com.nttdata.biblioteca.biblio.web;

import org.springframework.validation.Errors;

import com.nttdata.core.crud.web.CrudValidator;

public class BiblioValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
