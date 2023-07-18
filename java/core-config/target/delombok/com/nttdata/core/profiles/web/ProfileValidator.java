/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.web;

import org.springframework.validation.Errors;

import com.nttdata.core.profiles.constants.ProfileConstants;
import com.nttdata.core.profiles.model.Profile;
import com.nttdata.core.profiles.model.ProfileDataLoad;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;

/**
 * Validator for {@link Profile}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class ProfileValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Profile.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//The initial data loaded
		ProfileDataLoad initialData = (ProfileDataLoad) this.getValidationData();
				
		// Mandatory fields
		ValidatorUtils.rejectIfEmpty(errors, ProfileConstants.FIELD_NAME);
		ValidatorUtils.rejectIfEmpty(errors, ProfileConstants.FIELD_DESCRIPTION);
		ValidatorUtils.rejectIfEmptyCollection(errors, ProfileConstants.FIELD_AUTHORITIES);
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, ProfileConstants.FIELD_NAME, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, ProfileConstants.FIELD_DESCRIPTION, 250);
		
		//Allowed authorities
		ValidatorUtils.rejectIfCollectionNotContains(errors, ProfileConstants.FIELD_AUTHORITIES, null, CoreConstants.FIELD_ID, initialData.getAuthorities());
	}
}
