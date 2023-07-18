package com.nttdata.bootcamp.biblioteca.model;

import com.nttdata.core.common.model.Core;

import lombok.Data;

@Data
public class Autor extends Core<String> {
	private static final long serialVersionUID = 1L;
	private String nombre;
}


