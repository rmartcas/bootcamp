/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.model;

import java.util.List;

import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.model.CrudDataLoad;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MappingDataLoad entity
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MappingDataLoad extends CrudDataLoad {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The allowed mappings.
	 * @param mappings {@link List} of {@link Combo} The mappings
	 * @return {@link List} of {@link Combo} the mappings
	 */
	private List<Combo> mappings;
	
	/** 
	 * The allowed authorities.
	 * @param authorities {@link List} of {@link Combo} The authorities
	 * @return {@link List} of {@link Combo} the authorities
	 */
	private List<Combo> authorities;

}
