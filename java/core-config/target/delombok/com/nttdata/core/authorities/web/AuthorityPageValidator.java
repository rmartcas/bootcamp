/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nttdata.core.authorities.constants.AuthorityConstants;
import com.nttdata.core.authorities.model.AuthorityPage;
import com.nttdata.core.common.utils.ValidatorUtils;

/**
 * Validator for {@link AuthorityPage}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class AuthorityPageValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthorityPage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, AuthorityConstants.FIELD_FILTERS_NAME, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, AuthorityConstants.FIELD_FILTERS_DESCRIPTION, 250);
	}

}
