package com.nttdata.bootcamp.biblioteca.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.core.crud.mapper.CrudMapper;

@Mapper
public interface BibliotecaMapper extends CrudMapper<Libro>{
	

}
