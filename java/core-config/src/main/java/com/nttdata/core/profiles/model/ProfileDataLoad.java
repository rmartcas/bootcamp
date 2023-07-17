/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.model;

import java.util.List;

import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.model.CrudDataLoad;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ProfileDataLoad entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProfileDataLoad extends CrudDataLoad {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The allowed authorities.
	 * @param authorities {@link List} of {@link Combo} The authorities
	 * @return {@link List} of {@link Combo} the authorities
	 */
	private List<Combo> authorities;

}
