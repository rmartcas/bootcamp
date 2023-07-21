package com.nttdata.bootcamp.biblioteca.libro.web;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.libro.service.LibroService;
@ExtendWith(MockitoExtension.class)

class LibroControllerTest {
	@InjectMocks
	private LibroController libroController;
	
	@Mock
	private LibroService service;
//	@BeforeAll
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
	@Test
	void testGetService() {
		var srv=libroController.getService();
		assertNotNull(srv);
	}
	
	
	@Test
	void testGetValidator() {
		var validator=libroController.getValidator();
		assertNull(validator);
	}
	
	@Test
	void testGetSearchValidator() {
		var searchValidator=libroController.getSearchValidator();
		assertNull(searchValidator);
	}

}
