/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.exception;

import com.nttdata.core.common.exception.CoreException;

/**
 * Checked exception raised when the recaptcha validation failed for any reason.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class InvalidRecaptchaException extends CoreException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a invalid recaptcha exception.
	 *
	 * @param message the message
	 */
	public InvalidRecaptchaException(String message) {
		super(message);
	}
}
