package com.nttdata.bootcamp.demo.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.core.crud.web.CrudValidator;

public class DemoValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Demo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}