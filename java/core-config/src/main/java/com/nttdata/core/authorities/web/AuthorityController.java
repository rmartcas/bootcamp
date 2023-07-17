/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;
import com.nttdata.core.authorities.constants.AuthorityConstants;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.authorities.model.AuthorityPage;
import com.nttdata.core.authorities.service.AuthorityService;

/**
 * Authority controller to handle authorities
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestController
@RequestMapping(AuthorityConstants.CONTROLLER_NAMESPACE)
class AuthorityController implements CrudController<Authority, AuthorityPage> {

	/** The associated service */
	@Autowired
	private AuthorityService authorityService;
		
	@Override
	public CrudService<Authority> getService() {
		return authorityService;
	}

	@Override
	public Validator getValidator() {
		return new AuthorityValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new AuthorityPageValidator();
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
