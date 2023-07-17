/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.web;


import org.springframework.validation.Errors;

import com.nttdata.core.users.constants.UserConstants;
import com.nttdata.core.users.model.User;
import com.nttdata.core.users.model.UserDataLoad;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;

/**
 * Validator for {@link User}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class UserValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		//The initial data loaded
		UserDataLoad initialData = (UserDataLoad) this.getValidationData();
		
		// Mandatory fields
		ValidatorUtils.rejectIfEmpty(errors, UserConstants.FIELD_USERNAME);
		ValidatorUtils.rejectIfEmpty(errors, UserConstants.FIELD_NAME);
		ValidatorUtils.rejectIfEmpty(errors, UserConstants.FIELD_EMAIL);
		ValidatorUtils.rejectIfEmpty(errors, UserConstants.FIELD_PROFILE + CommonConstants.DOT + CoreConstants.FIELD_ID);
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, UserConstants.FIELD_USERNAME, 10);
		ValidatorUtils.rejectIfLengthExceeded(errors, UserConstants.FIELD_NAME, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, UserConstants.FIELD_EMAIL, 100);
		
		//Allowed profiles
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				UserConstants.FIELD_PROFILE + CommonConstants.DOT + CoreConstants.FIELD_ID, initialData.getProfiles());
		
	}
}
