package com.nttdata.bootcamp.biblioteca.autor.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.core.crud.mapper.CrudMapper;
@Mapper
public interface AutorMapper extends CrudMapper<Autor>{

}
