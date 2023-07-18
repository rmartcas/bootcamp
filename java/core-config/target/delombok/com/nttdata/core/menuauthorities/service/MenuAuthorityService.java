/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menuauthorities.service;

import com.nttdata.core.menu.model.Menu;

/**
 * Menu Authority service interface to handle authorities and menu associations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface MenuAuthorityService {
	
	/**
	 * Inserts a list of authorities associated with the menu
	 * 
	 * @param dto the menu
	 */
	void insertAuthorities(Menu dto);
	
	/**
	 * Delete all authorities associated with the menu
	 * 
	 * @param dto the menu
	 */
	void deleteAuthorities(Menu dto);
}
