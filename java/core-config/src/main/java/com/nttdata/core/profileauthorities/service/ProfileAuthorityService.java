/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profileauthorities.service;

import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.profiles.model.Profile;

/**
 * Profile Authority service interface to handle authorities and profile associations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface ProfileAuthorityService {
	
	/**
	 * Inserts a list of authorities associated with the profile
	 * 
	 * @param profile the profile
	 */
	void insertAuthorities(Profile profile);
	
	/**
	 * Inserts a list of profiles associated with the authority
	 * 
	 * @param authority the authority
	 */
	void insertProfiles(Authority authority);
	
	/**
	 * Delete all authorities associated with the profile
	 * 
	 * @param profile the profile
	 */
	void deleteAuthorities(Profile profile);
	
	/**
	 * Delete all profiles associated with the authority
	 * 
	 * @param authority the authority
	 */
	void deleteProfiles(Authority authority);
}
