/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.web;

import org.springframework.validation.Errors;

import com.nttdata.core.audit.constants.AuditableConstants;
import com.nttdata.core.audit.model.AuditDataLoad;
import com.nttdata.core.audit.model.AuditPage;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;

/**
 * Validator for {@link AuditPage}
 */
public class AuditPageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AuditPage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuditDataLoad initialData = (AuditDataLoad) this.getValidationData();

		AuditPage page = (AuditPage) target;

		// Mandatory fields
		Object requestId = errors.getFieldValue(AuditableConstants.FIELD_FILTERS_REQUEST_ID);
		if (null == requestId || 0 == page.getFilters().getRequestId().length()) {
			ValidatorUtils.rejectIfEmpty(errors, AuditableConstants.FIELD_FILTERS_TABLE);
		}
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, AuditableConstants.FIELD_FILTERS_USER, 10);
		ValidatorUtils.rejectIfLengthExceeded(errors, AuditableConstants.FIELD_FILTERS_REQUEST_ID, 36);
		ValidatorUtils.rejectIfLengthExceeded(errors, AuditableConstants.FIELD_FILTERS_PAIR_KEY, 36);
		
		//Allowed tables and actions
		if (null != page.getFilters().getTable()) {
			ValidatorUtils.rejectIfCollectionNotContains(errors, AuditableConstants.FIELD_FILTERS_TABLE, initialData.getTables(), true);
		}

		if (null != page.getFilters().getAction()) {
			ValidatorUtils.rejectIfCollectionNotContains(errors, AuditableConstants.FIELD_FILTERS_ACTION, initialData.getActions());	
		}
	}
}
