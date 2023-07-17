/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappingauthorities.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.mappingauthorities.model.MappingAuthority;

/**
 * Interface to handle authorities and mappings associations.
 * 
 * Allow batch insert of:
 * 	- Authorities associated with a mapping
 * 
 * Allow bulk delete of:
 * 	- All authorities associated with a mapping
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface MappingAuthorityMapper {
	
	/**
	 * Inserts a mapping association with an authority
	 * 
	 * @param dto the mapping and authority to associate
	 */
	void insert(MappingAuthority dto);
	
	/**
	 * Delete all authorities associated with the mapping
	 * 
	 * @param dto the mapping
	 */
	void deleteAuthoritiesByMapping(MappingAuthority dto);
}
