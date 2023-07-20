package com.nttdata.biblioteca.biblio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.biblioteca.biblio.mapper.BiblioMapper;
import com.nttdata.biblioteca.biblio.model.Libro;
import com.nttdata.biblioteca.biblio.service.BiblioService;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class BiblioServiceImpl implements BiblioService {

	@Autowired
	private BiblioMapper mapper;

	@Override
	public CrudMapper<Libro> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}
	



}
