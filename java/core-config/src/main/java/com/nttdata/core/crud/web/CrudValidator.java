/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.web;

import org.springframework.validation.Validator;

import com.nttdata.core.common.model.DataLoad;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic crud validator implementing {@link Validator} for crud controllers
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
public abstract class CrudValidator implements Validator {
	
	/** 
	 * The validation data used to validate user inputs
	 * 
	 * @param validationData The validation data
	 * @return The validation data
	 * */
	private DataLoad validationData;
}