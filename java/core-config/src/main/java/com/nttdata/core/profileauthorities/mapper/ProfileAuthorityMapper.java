/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profileauthorities.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.profileauthorities.model.ProfileAuthority;

/**
 * Interface to handle authorities and profiles associations.
 * 
 * Allow batch insert of:
 * 	- Authorities associated with a profile
 * 	- Profiles associated with an authority
 * 
 * Allow bulk delete of:
 * 	- All authorities associated with a profile
 *  - All profiles associated with an authority
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface ProfileAuthorityMapper {
	
	/**
	 * Inserts a profile association with an authority
	 * 
	 * @param dto the profile and authory to associate
	 */
	void insert(ProfileAuthority dto);
	
	/**
	 * Delete all authorities associated with the profile
	 * 
	 * @param dto the profile
	 */
	void deleteAuthoritiesByProfile(ProfileAuthority dto);
	
	/**
	 * Delete all profiles associated with the authority
	 * 
	 * @param dto the authority
	 */
	void deleteProfilesByAuthority(ProfileAuthority dto);
	
	

}
