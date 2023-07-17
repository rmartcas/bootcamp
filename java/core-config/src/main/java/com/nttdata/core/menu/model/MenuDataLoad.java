/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.model;

import java.util.List;

import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.model.CrudDataLoad;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MenuDataLoad entity
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MenuDataLoad extends CrudDataLoad {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The allowed authorities.
	 * @param authorities {@link List} of {@link Combo} The authorities
	 * @return {@link List} of {@link Combo} the authorities
	 */
	private List<Combo> authorities;
	
	/** 
	 * The allowed parents.
	 * @param parents {@link List} of {@link Combo} The parents
	 * @return {@link List} of {@link Combo} the parents
	 */
	private List<Combo> parents;

}
