/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nttdata.core.profiles.constants.ProfileConstants;
import com.nttdata.core.profiles.model.ProfilePage;
import com.nttdata.core.common.utils.ValidatorUtils;

/**
 * Validator for {@link ProfilePage}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class ProfilePageValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfilePage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, ProfileConstants.FIELD_FILTERS_NAME, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, ProfileConstants.FIELD_FILTERS_DESCRIPTION, 250);
	}

}
