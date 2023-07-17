/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profileauthorities.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.profiles.model.Profile;

/**
 * Profile Authority entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProfileAuthority extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The profile associated 
	 * @param profile {@link List} The profiles
	 * @return {@link List} the profiles
	 */
	private Profile profile = new Profile();
	
	/**
	 * The authority associated
 	 * @param authority {@link List} The authority
	 * @return {@link List} the authority
	 */
	private Authority authority = new Authority();
}
