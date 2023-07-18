/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.web;

import org.springframework.validation.Errors;

import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;
import com.nttdata.core.menu.constants.MenuConstants;
import com.nttdata.core.menu.model.MenuDataLoad;
import com.nttdata.core.menu.model.MenuPage;

/**
 * Validator for {@link MenuPage}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class MenuPageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MenuPage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MenuDataLoad initialData = (MenuDataLoad) this.getValidationData();
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_FILTERS_TITLE, 250);
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_FILTERS_LINK, 250);
		
		//Allowed authorities
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				MenuConstants.FIELD_FILTERS_AUTHORITIES, null, CoreConstants.FIELD_ID, initialData.getAuthorities());
	}

}
