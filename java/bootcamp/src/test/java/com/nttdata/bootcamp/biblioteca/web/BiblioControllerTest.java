package com.nttdata.bootcamp.biblioteca.web;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.service.BiblioService;

@ExtendWith(MockitoExtension.class)

/*
   //@BeforeEach
   // void setup() {
   //     MockitoAnnotations.openMocks(this);
    //}
 *
 * */
@ExtendWith(MockitoExtension.class)
class BiblioControllerTest {
	
	
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
