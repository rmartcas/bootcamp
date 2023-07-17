/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Recaptcha entity
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Recaptcha {
	
	/** 
	 * whether this request was a valid reCAPTCHA token for your site.
	 * @param success {@link Boolean} The success
	 * @return {@link Boolean} the success
	 */
	private boolean success;
	
	/** 
	 * the score for this request (0.0 - 1.0).
	 * @param score {@link Float} The score
	 * @return {@link Float} the score
	 */
	private float score;
	
	/** 
	 * the action name for this request (important to verify).
	 * @param action {@link String} The action
	 * @return {@link String} the action
	 */
	private String action;
	
	/** 
	 * timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ).
	 * @param challengeTs {@link Date} The challengeTs
	 * @return {@link Date} the challengeTs
	 */
	@JsonProperty("challenge_ts")
	private Date challengeTs;
	
	/** 
	 * the hostname of the site where the reCAPTCHA was solved.
	 * @param hostname {@link String} The hostname
	 * @return {@link String} the hostname
	 */
	private String hostname;
	
	/** 
	 * the error codes in recaptcha validation.
	 * @param errorCodes {@link List} The errorCodes
	 * @return {@link List} the errorCodes
	 */
	@JsonProperty("error-codes")
	private List<String> errorCodes;  
}