package com.nttdata.bootcamp.biblioteca.libro.service.impl;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.libro.mapper.LibroMapper;
@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {
	@InjectMocks
	private LibroServiceImpl service;
	
	@Mock
	private LibroMapper mapper;
	
	@Test
	void testGetMapper() {
		assertSame(mapper, service.getMapper());
	}

}
