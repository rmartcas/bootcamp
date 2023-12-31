/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.exception;

import org.springframework.validation.Errors;

import lombok.Getter;

import com.nttdata.core.i18n.constants.I18nConstants;

/**
 * Web exception for rest operation that has bad request or invalid input data
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
public class BadRequestException extends CoreException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14443223429L;
	
	/** 
	 * The errors detected
	 * @return {@link Errors} the errors
	 */
	private final transient Errors errors;
	
	/**
	 * Instantiates a bad request exception with specified errors and default message.
	 *
	 * @param errors produced in request
	 */
	public BadRequestException(Errors errors) {
		super(I18nConstants.I18N_VALIDATION_INVALID_DATA);
		this.errors = errors;
	}

	/**
	 * Instantiates a bad request exception with specified message.
	 *
	 * @param message the message
	 */
	public BadRequestException(String message) {
		super(message);
		this.errors = null;
	}
	
	/**
	 * Instantiates a bad request exception with specified message and errors
	 *
	 * @param message the message
	 * @param errors produced in request
	 */
	public BadRequestException(String message, Errors errors) {
		super(message);
		this.errors = errors;
	}
}
