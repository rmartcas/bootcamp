/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.web;

import org.springframework.validation.Errors;

import com.nttdata.core.audit.constants.AuditableConstants;
import com.nttdata.core.audit.model.AuditDataLoad;
import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;

/**
 * Validator for {@link Audit}
 */
public class AuditValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Audit.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuditDataLoad initialData = (AuditDataLoad) this.getValidationData();
		
		ValidatorUtils.rejectIfEmpty(errors, AuditableConstants.FIELD_TABLE);
		ValidatorUtils.rejectIfEmpty(errors, AuditableConstants.FIELD_PAIR_KEY);
		
		ValidatorUtils.rejectIfCollectionNotContains(errors, AuditableConstants.FIELD_TABLE, initialData.getTables(), true);
		ValidatorUtils.rejectIfLengthExceeded(errors, AuditableConstants.FIELD_PAIR_KEY, 36);
	}
}
