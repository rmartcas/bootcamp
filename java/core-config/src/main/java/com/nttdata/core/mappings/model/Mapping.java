/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.model;

import java.util.List;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.authorities.model.Authority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mapping entity
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Mapping extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The mapping url pattern.
	 * @param pattern {@link String} The pattern
	 * @return {@link String} the pattern
	 */
	private String pattern;
	
	/** 
	 * The mapping position order to evaluate.
	 * @param position {@link Byte} The position
	 * @return {@link Byte} the position
	 */
	private Byte position;
	
	/** 
	 * The associated authorities which can access this mapping.
	 * @param authorities {@link List} The authorities
	 * @return {@link List} the authorities
	 */
	private List<Authority> authorities;
}
