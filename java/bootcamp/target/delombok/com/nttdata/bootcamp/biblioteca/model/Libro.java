package com.nttdata.bootcamp.biblioteca.model;

import com.nttdata.core.common.model.Core;

public class Libro extends Core<Long> {
	
	private static final long serialVersionUID=1L;	
	private String titulo;
	private Autor autor;
 
}
