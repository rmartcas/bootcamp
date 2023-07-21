package com.nttdata.bootcamp.biblioteca.autor.web;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.autor.service.AutorService;

@ExtendWith(MockitoExtension.class)

class AutorControllerTest {
	@InjectMocks
	private AutorController autorController;
	
	@Mock
	private AutorService service;
//	@BeforeAll
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
	@Test
	void testGetService() {
		var srv=autorController.getService();
		assertNotNull(srv);
	}
	
	
	@Test
	void testGetValidator() {
		var validator=autorController.getValidator();
		assertNull(validator);
	}
	
	@Test
	void testGetSearchValidator() {
		var searchValidator=autorController.getSearchValidator();
		assertNull(searchValidator);
	}
	

}
