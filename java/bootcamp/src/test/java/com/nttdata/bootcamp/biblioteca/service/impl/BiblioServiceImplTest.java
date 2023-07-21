package com.nttdata.bootcamp.biblioteca.service.impl;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.mapper.BibliotecaMapper;

@ExtendWith(MockitoExtension.class)
class BiblioServiceImplTest {

	@InjectMocks
	private BiblioServiceImpl service;
	
	@Mock
	private BibliotecaMapper mapper;
	
	@Test
	void testGetMapper() {
		assertSame(mapper, service.getMapper());
	}

}
