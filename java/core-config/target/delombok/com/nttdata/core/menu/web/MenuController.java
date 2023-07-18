/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;
import com.nttdata.core.menu.constants.MenuConstants;
import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.menu.model.MenuPage;
import com.nttdata.core.menu.service.MenuService;

/**
 * Controller to handle menus
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestController
@RequestMapping(MenuConstants.CONTROLLER_NAMESPACE)
class MenuController implements CrudController<Menu, MenuPage> {

	/** The associated service */
	@Autowired
	private MenuService service;
		
	@Override
	public CrudService<Menu> getService() {
		return service;
	}

	@Override
	public Validator getValidator() {
		return new MenuValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new MenuPageValidator();
	}
	
	@Override
	public Validator getDeleteValidator() {
		//No data validation needed to delete a record
		return null;
	}
	
	@Override
	public Validator getFindValidator() {
		//No data validation needed to find a record
		return null;
	}
}
