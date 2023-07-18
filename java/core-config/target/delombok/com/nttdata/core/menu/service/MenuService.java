/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.service;

import java.util.List;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.menu.model.Menu;

/**
 * Menu service of application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface MenuService extends CrudService<Menu> {
	
	/**
	 * Find all available menu options for the user.
	 * 
	 * @param user {@link CoreUser} the user that requested its menu
	 * @return {@link List} of {@link Menu} with records
	 * @throws CoreException in case of error
	 */
	List<Menu> getUserMenu(CoreUser user) throws CoreException;
}
