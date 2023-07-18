package com.nttdata.bootcamp.demo.web;

import org.springframework.validation.Errors;

import com.nttdata.bootcamp.demo.model.DemoPage;
import com.nttdata.core.crud.web.CrudValidator;

public class PageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return DemoPage.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
