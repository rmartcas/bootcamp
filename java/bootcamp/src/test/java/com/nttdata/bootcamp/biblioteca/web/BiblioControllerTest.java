package com.nttdata.bootcamp.biblioteca.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Validator;

import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.service.BiblioService;
import com.nttdata.core.crud.service.CrudService;

class BiblioControllerTest {

	@InjectMocks
	private BiblioController controller;
	
	@Mock
	private BiblioService service;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetService() {
		CrudService<Libro> service2 = controller.getService();
		Assertions.assertSame(service, service2);
	}
	
	@Test
	void testGetValidator() {
		Validator validator = controller.getValidator();
		Assertions.assertNotNull(validator);
	}
	
	@Test
	void testGetSearchValidator() {
		Validator validator = controller.getSearchValidator();
		Assertions.assertNull(validator);
	}

	

}
