package com.nttdata.bootcamp.biblioteca.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.biblioteca.mapper.BiblioMapper;
import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.service.BiblioService;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class BiblioServiceImpl implements BiblioService{
	
	@Autowired
	private BiblioMapper mapper;
	
	@Override
	public CrudMapper<Libro> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}
	

}
