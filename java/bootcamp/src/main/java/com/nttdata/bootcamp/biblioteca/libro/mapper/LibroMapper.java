package com.nttdata.bootcamp.biblioteca.libro.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.bootcamp.biblioteca.libro.model.Libro;
import com.nttdata.core.crud.mapper.CrudMapper;
@Mapper
public interface LibroMapper extends CrudMapper<Libro>{

}
