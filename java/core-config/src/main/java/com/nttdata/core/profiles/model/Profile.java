/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.authorities.model.Authority;

/**
 * Profile entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Profile extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The profile name.
	 * @param name {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
	/**
	 * The profile description
	 * @param description {@link String} The description
	 * @return {@link String} the description 
	 */
	private String description;
	
	/** 
	 * The list of authorities associated with this profile
	 * @param authorities {@link List} The authorities
	 * @return {@link List} the authorities
	 */
	private List<Authority> authorities;
	
	/**
	 * If the profile is default.
	 * @param isDefault {@link boolean} if is default
	 * @return {@link boolean} is default or not
	 */
	private boolean isDefault;
}
