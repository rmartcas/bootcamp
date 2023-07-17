/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.profiles.model.Profile;

/**
 * Authority entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Authority extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The authority name.
	 * @param name {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
	/**
	 * The authority description
	 * @param description {@link String} The description
	 * @return {@link String} the description 
	 */
	private String description;
	
	/** 
	 * The list of profiles associated with this authority
	 * @param profiles {@link List} The profiles
	 * @return {@link List} the profiles
	 */
	private List<Profile> profiles;
}
