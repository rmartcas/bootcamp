/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.web;

import org.springframework.validation.Errors;

import com.nttdata.core.authorities.constants.AuthorityConstants;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.authorities.model.AuthorityDataLoad;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;

/**
 * Validator for {@link Authority}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class AuthorityValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Authority.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		//The initial data loaded
		AuthorityDataLoad initialData = (AuthorityDataLoad) this.getValidationData();
		
		// Mandatory fields
		ValidatorUtils.rejectIfEmpty(errors, AuthorityConstants.FIELD_NAME);
		ValidatorUtils.rejectIfEmpty(errors, AuthorityConstants.FIELD_DESCRIPTION);
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, AuthorityConstants.FIELD_NAME, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, AuthorityConstants.FIELD_DESCRIPTION, 250);
		
		//Allowed profiles
		ValidatorUtils.rejectIfCollectionNotContains(errors, AuthorityConstants.FIELD_PROFILES, null, CoreConstants.FIELD_ID, initialData.getProfiles());
		
	}
}
