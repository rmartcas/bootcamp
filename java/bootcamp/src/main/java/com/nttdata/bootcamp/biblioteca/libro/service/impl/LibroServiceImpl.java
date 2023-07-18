package com.nttdata.bootcamp.biblioteca.libro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.biblioteca.libro.mapper.LibroMapper;
import com.nttdata.bootcamp.biblioteca.libro.model.Libro;
import com.nttdata.bootcamp.biblioteca.libro.service.LibroService;
import com.nttdata.core.crud.mapper.CrudMapper;
@Service
public class LibroServiceImpl implements LibroService{
	@Autowired
	private LibroMapper mapper;
	@Override
	public CrudMapper<Libro> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}
