/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Recaptcha Config data for application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RecaptchaConfig {
	
	/** 
	 * The recaptcha site key if recaptcha is enabled.
	 * @param siteKey {@link String} The siteKey
	 * @return {@link String} the siteKey
	 */
	private String siteKey;
	
	/** 
	 * The recaptcha header name where submit the recaptcha token.
	 * @param headerName {@link String} The headerName
	 * @return {@link String} the headerName
	 */
	private String headerName;
}
