package com.nttdata.bootcamp.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.bootcamp.demo.model.Demo;
import com.nttdata.core.crud.mapper.CrudMapper;

@Mapper
public interface DemoMapper extends CrudMapper <Demo>{

}
