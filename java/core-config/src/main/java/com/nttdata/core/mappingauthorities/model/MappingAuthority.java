/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappingauthorities.model;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.authorities.model.Authority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mapping Authority entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MappingAuthority extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The mapping associated.
	 * @param mapping {@link Mapping} The mapping
	 * @return {@link Mapping} the mapping
	 */
	private Mapping mapping = new Mapping();
	
	/** 
	 * The authority associated.
	 * @param authority {@link Authority} The authority
	 * @return {@link Authority} the authority
	 */
	private Authority authority = new Authority();
}
