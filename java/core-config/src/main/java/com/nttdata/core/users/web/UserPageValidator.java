/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.web;

import org.springframework.validation.Errors;

import com.nttdata.core.users.constants.UserConstants;
import com.nttdata.core.users.model.UserDataLoad;
import com.nttdata.core.users.model.UserPage;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;

/**
 * Validator for {@link UserPage}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class UserPageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDataLoad initialData = (UserDataLoad) this.getValidationData();
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, UserConstants.FIELD_FILTERS_USERNAME, 10);
		ValidatorUtils.rejectIfLengthExceeded(errors, UserConstants.FIELD_FILTERS_NAME, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, UserConstants.FIELD_FILTERS_EMAIL, 100);
		
		//Allowed profiles
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				UserConstants.FIELD_FILTERS_PROFILE + CommonConstants.DOT + CoreConstants.FIELD_ID, initialData.getProfiles());
	}
}
