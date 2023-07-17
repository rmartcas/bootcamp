/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.web;


import org.springframework.validation.Errors;

import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;
import com.nttdata.core.mappings.constants.MappingConstants;
import com.nttdata.core.mappings.model.MappingDataLoad;
import com.nttdata.core.mappings.model.MappingPage;

/**
 * Validator for {@link MappingPage}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class MappingPageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MappingPage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MappingDataLoad initialData = (MappingDataLoad) this.getValidationData();
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, MappingConstants.FIELD_FILTERS_PATTERN, 250);
		ValidatorUtils.rejectIfLengthExceeded(errors, MappingConstants.FIELD_FILTERS_POSITION, 2);
		
		//Allowed authorities
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				MappingConstants.FIELD_FILTERS_AUTHORITIES, null, CoreConstants.FIELD_ID, initialData.getAuthorities());
	}

}
