package com.nttdata.bootcamp.biblioteca.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.biblioteca.mapper.BibliotecaMapper;
import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.service.BibliotecaService;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class BibliotecaServiceImpl implements BibliotecaService{

	@Autowired
	private BibliotecaMapper mapper;

	@Override
	public CrudMapper<Libro> getMapper() {
		return mapper;
	}
	


}
