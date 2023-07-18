package com.nttdata.biblioteca.biblio.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.core.crud.mapper.CrudMapper;

@Mapper
public interface BiblioMapper extends CrudMapper<Demo> {

}
