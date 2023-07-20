package com.nttdata.bootcamp.biblioteca.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.bootcamp.biblioteca.mapper.BiblioMapper;
import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.bootcamp.biblioteca.service.BiblioService;
import com.nttdata.core.crud.mapper.CrudMapper;

public class BilblioServiceImpl implements BiblioService{

	@Override
	public CrudMapper<Libro> getMapper() {
		// TODO Auto-generated method stub
		return Mapper;
	}
	@Autowired
	private BiblioMapper Mapper;

}
