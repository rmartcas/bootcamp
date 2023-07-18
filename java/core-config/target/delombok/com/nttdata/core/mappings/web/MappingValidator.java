/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.web;

import org.springframework.validation.Errors;

import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;
import com.nttdata.core.mappings.constants.MappingConstants;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.mappings.model.MappingDataLoad;

/**
 * Validator for {@link Mapping}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class MappingValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Mapping.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		//The initial data loaded
		MappingDataLoad initialData = (MappingDataLoad) this.getValidationData();
		
		// Mandatory fields
		ValidatorUtils.rejectIfEmpty(errors, MappingConstants.FIELD_PATTERN);
		ValidatorUtils.rejectIfEmptyCollection(errors, MappingConstants.FIELD_AUTHORITIES);
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, MappingConstants.FIELD_PATTERN, 250);
		ValidatorUtils.rejectIfLengthExceeded(errors, MappingConstants.FIELD_POSITION, 2);
		
		//Allowed authorities
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				MappingConstants.FIELD_AUTHORITIES, null, CoreConstants.FIELD_ID, initialData.getAuthorities());
        //Allowed mappings
        ValidatorUtils.rejectIfCollectionNotContains(errors, MappingConstants.FIELD_PATTERN, initialData.getMappings());
		
	}
}
