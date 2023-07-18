// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.web;

import org.springframework.validation.Validator;
import com.nttdata.core.common.model.DataLoad;

/**
 * Generic crud validator implementing {@link Validator} for crud controllers
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public abstract class CrudValidator implements Validator {
	/**
	 * The validation data used to validate user inputs
	 * 
	 */
	private DataLoad validationData;

	/**
	 * The validation data used to validate user inputs
	 * 
	 * @return The validation data
	 */
	@java.lang.SuppressWarnings("all")
	public DataLoad getValidationData() {
		return this.validationData;
	}

	/**
	 * The validation data used to validate user inputs
	 * 
	 * @param validationData The validation data
	 */
	@java.lang.SuppressWarnings("all")
	public void setValidationData(final DataLoad validationData) {
		this.validationData = validationData;
	}
}
