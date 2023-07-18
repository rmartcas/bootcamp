package com.nttdata.bootcamp.biblioteca.autor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.biblioteca.autor.mapper.AutorMapper;
import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.bootcamp.biblioteca.autor.service.AutorService;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class AutorServiceImpl implements AutorService  {
	@Autowired
	private AutorMapper mapper;
	@Override
	public CrudMapper<Autor> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}
