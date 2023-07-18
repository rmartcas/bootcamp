package com.nttdata.bootcamp.biblioteca.libro.model;

import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.core.common.model.Core;

public class Libro extends Core<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private Autor autor;
	

}
