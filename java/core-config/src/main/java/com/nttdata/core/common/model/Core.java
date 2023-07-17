/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Core dto for application, all others dtos should extends of this.<br>
 * 
 * Uses a generic type <code>E</code> to determine the id field
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public abstract class Core<E extends Serializable> implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * unique id of entity.
	 * @param id The id
	 * @return the id
	 */
    private E id;
}
