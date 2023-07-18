/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.service;

import com.nttdata.core.common.exception.CoreException;
/**
 * Recaptcha service interface
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface RecaptchaService {

	/**
	 * Validate a {@link com.nttdata.core.security.recaptcha.model.Recaptcha} request.
	 * @param recaptchaToken {@link String} to validate
	 * @param action {@link String} the action to validate
	 * @throws CoreException if validation fails
	 */
	void validate(String recaptchaToken, String action) throws CoreException;
}