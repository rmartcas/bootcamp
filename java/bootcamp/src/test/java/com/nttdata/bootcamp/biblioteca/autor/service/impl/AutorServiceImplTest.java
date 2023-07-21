package com.nttdata.bootcamp.biblioteca.autor.service.impl;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.bootcamp.biblioteca.autor.mapper.AutorMapper;
@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {
	@InjectMocks
	private AutorServiceImpl service;
	
	@Mock
	private AutorMapper mapper;
	@Test
	void test() {
		assertSame(mapper, service.getMapper());
	}

}
