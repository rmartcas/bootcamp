package com.nttdata.bootcamp.biblioteca.web;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.service.BiblioService;

@ExtendWith(MockitoExtension.class)
public class BiblioControllerTest {

	@InjectMocks
	BiblioController controller;
	
	@Mock
	private BiblioService service;
	
	@Test
	void testGetService() {
		assertNotNull(controller.getService());
	}
}
