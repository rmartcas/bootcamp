package com.nttdata.bootcamp.biblioteca.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.service.BiblioService;

@ExtendWith(MockitoExtension.class)
class BiblioControllerTest {

//	@BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
	
	@InjectMocks
	BiblioController controller;
	
	@Mock
	private BiblioService service;
	
	@Test
	void testGetService() {
		assertNotNull(controller.getService());
	}
	@Test
	void testGetValidator() {
		assertNull(controller.getValidator());
	}
	@Test
	void testGetSearchValidator() {
		assertNull(controller.getSearchValidator());
	}

}
