package com.nttdata.bootcamp.biblioteca.libro.model;

import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.core.common.model.Core;
import lombok.Data;

@Data
public class Libro extends Core<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String titulo;
	private Autor autor;
	

}
