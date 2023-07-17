/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menuauthorities.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.menuauthorities.model.MenuAuthority;

/**
 * Interface to handle authorities and menu associations.
 * 
 * Allow batch insert of:
 * 	- Authorities associated with a menu
 * 
 * Allow bulk delete of:
 * 	- All authorities associated with a menu
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface MenuAuthorityMapper {
	
	/**
	 * Inserts a menu association with an authority
	 * 
	 * @param dto the menu and authority to associate
	 */
	void insert(MenuAuthority dto);
	
	/**
	 * Delete all authorities associated with the menu
	 * 
	 * @param dto the menu
	 */
	void delete(MenuAuthority dto);
}
