/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;
import com.nttdata.core.users.constants.UserConstants;
import com.nttdata.core.users.model.User;
import com.nttdata.core.users.model.UserPage;
import com.nttdata.core.users.service.UserService;

/**
 * User controller to handle users
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestController
@RequestMapping(UserConstants.CONTROLLER_NAMESPACE)
class UserController implements CrudController<User, UserPage> {

	/** The associated service */
	@Autowired
	private UserService userService;
		
	@Override
	public CrudService<User> getService() {
		return userService;
	}

	@Override
	public Validator getValidator() {
		return new UserValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new UserPageValidator();
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
