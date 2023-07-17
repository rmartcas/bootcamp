/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappingauthorities.service;

import com.nttdata.core.mappings.model.Mapping;

/**
 * Mapping Authority service interface to handle authorities and mapping associations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface MappingAuthorityService {
	
	/**
	 * Inserts a list of authorities associated with the mapping
	 * 
	 * @param dto the mapping
	 */
	void insertAuthorities(Mapping dto);
	
	/**
	 * Delete all authorities associated with the mapping
	 * 
	 * @param dto the mapping
	 */
	void deleteAuthorities(Mapping dto);
}
