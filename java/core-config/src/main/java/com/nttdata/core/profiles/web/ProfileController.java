/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;
import com.nttdata.core.profiles.constants.ProfileConstants;
import com.nttdata.core.profiles.model.Profile;
import com.nttdata.core.profiles.model.ProfilePage;
import com.nttdata.core.profiles.service.ProfileService;

/**
 * Profile controller to handle profiles
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestController
@RequestMapping(ProfileConstants.CONTROLLER_NAMESPACE)
class ProfileController implements CrudController<Profile, ProfilePage> {

	/** The associated profile service */
	@Autowired
	private ProfileService profileService;
	
	@Override
	public CrudService<Profile> getService() {
		return profileService;
	}

	@Override
	public Validator getValidator() {
		return new ProfileValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new ProfilePageValidator();
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
