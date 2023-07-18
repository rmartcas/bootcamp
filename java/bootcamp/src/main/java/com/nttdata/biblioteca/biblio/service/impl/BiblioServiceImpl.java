package com.nttdata.biblioteca.biblio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.biblioteca.biblio.mapper.BiblioMapper;
import com.nttdata.biblioteca.biblio.mapper.BiblioService;
import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.core.crud.mapper.CrudMapper;

@Service
public class BiblioServiceImpl implements BiblioService {

	@Autowired
	private BiblioMapper mapper;
	
	@Override
	public CrudMapper<Demo> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}


}
