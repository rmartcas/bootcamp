/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.web;

import org.springframework.validation.Errors;


import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.crud.web.CrudValidator;
import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.core.menu.constants.MenuConstants;
import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.menu.model.MenuDataLoad;

/**
 * Validator for {@link Menu}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class MenuValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Menu.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		//The initial data loaded
		Menu menu = (Menu) target;
		MenuDataLoad initialData = (MenuDataLoad) this.getValidationData();
		
		// Mandatory fields
		ValidatorUtils.rejectIfEmpty(errors, MenuConstants.FIELD_TITLE);
		ValidatorUtils.rejectIfEmpty(errors, MenuConstants.FIELD_LINK);
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_TITLE, 250);
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_LINK, 250);
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_ICON, 100);
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_POSITION, 2);
		ValidatorUtils.rejectIfLengthExceeded(errors, MenuConstants.FIELD_PARENT + CommonConstants.DOT + CoreConstants.FIELD_ID, 5);
		
		//Allowed authorities
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				MenuConstants.FIELD_AUTHORITIES, null, CoreConstants.FIELD_ID, initialData.getAuthorities());
		
		//Allowed parents
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				MenuConstants.FIELD_PARENT + CommonConstants.DOT + CoreConstants.FIELD_ID, initialData.getParents());
		
		// If not is a new entry, the id and the parent id must not be the same
		if (null != menu.getId() && menu.getId().equals(menu.getParent().getId())) {
			errors.rejectValue(MenuConstants.FIELD_PARENT, I18nConstants.I18N_VALIDATION_FIELD_INVALID,
					new Object[] {ValidatorUtils.getFullFieldName(errors, MenuConstants.FIELD_PARENT)}, null);
		}
	}
}
