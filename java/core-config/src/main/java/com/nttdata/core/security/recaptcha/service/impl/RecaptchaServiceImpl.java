/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.service.impl;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.context.ApplicationEventPublisherHolder;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.context.ConfigProperties.Security.Recaptcha.RecaptchaAction;
import com.nttdata.core.security.events.CommunicationEvent;
import com.nttdata.core.security.recaptcha.exception.InvalidRecaptchaException;
import com.nttdata.core.security.recaptcha.model.Recaptcha;
import com.nttdata.core.security.recaptcha.service.RecaptchaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Recaptcha service implementation
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix="config", name={"security.recaptcha.enabled"}, havingValue="true", matchIfMissing = false)
public class RecaptchaServiceImpl implements RecaptchaService {
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	/** The rest template to call verify url */
	@Autowired
    private RestOperations restTemplate;
 
	/** Token pattern validation */
    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

	/**
	 * Validate a {@link Recaptcha} request.
	 * If the response is not returned by the recaptcha verify url the request is considered valid.
	 * 
	 * @param recaptchaToken {@link String} to validate
	 * @param action {@link String} the action to validate
	 * @throws CoreException if validation fails
	 */
	@Override
	public void validate(String recaptchaToken, String action) throws CoreException {
		if(!responseSanityCheck(recaptchaToken)) {
            throw new InvalidRecaptchaException("Response contains invalid characters");
        }
		
		final com.nttdata.core.context.ConfigProperties.Security.Recaptcha recaptchaConfig = configProperties.getSecurity().getRecaptcha();
		
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", recaptchaConfig.getKeySecret());
        requestMap.add("response", recaptchaToken);
        
        Recaptcha recaptchaResponse = null;
        try {
        	recaptchaResponse = restTemplate.postForObject(recaptchaConfig.getVerifyUrl(), requestMap, Recaptcha.class);
		} catch (Exception e) {
			log.error("Cannot get the reCaptcha response from verify url. Request should be considered as valid.", e);
			ApplicationEventPublisherHolder.getEventPublisher().publishEvent(new CommunicationEvent(recaptchaConfig.getVerifyUrl()));
			return;
		}

        verifyResponse(recaptchaConfig, recaptchaResponse, action);
	}
	
	/**
	 * Verify that the token match with a valid pattern
	 * @param recaptchaToken {@link String} to check
	 * @return boolean true if is valid pattern
	 */
	private boolean responseSanityCheck(String recaptchaToken) {
        return StringUtils.hasLength(recaptchaToken) && RESPONSE_PATTERN.matcher(recaptchaToken).matches();
    }
	
	/**
	 * Validate the response.
	 * Check if the response action has a custom action setting in <code>recaptchaConfig</code>.
	 * <ul>
	 * <li>If recaptchaResponse is null validation is skiped and is assumed as valid.</li>
	 * <li>If recaptchaResponse is not success InvalidRecaptchaException is thrown.</li>
	 * <li>If a custom action is defined, is enabled and the score is valid for the response returns true.</li>
	 * <li>If a custom action is defined but not is enabled, then validation is skipped and is considered valid returning true.</li>
	 * <li>If not custom action is defined, global score is applied to the response.</li>
	 * </ul>
	 * @param recaptchaConfig {@link com.nttdata.core.context.ConfigProperties.Security.Recaptcha} with the validation settings
	 * @param recaptchaResponse {@link Recaptcha} the response to check
	 * @return boolean
	 */
	private void verifyResponse(com.nttdata.core.context.ConfigProperties.Security.Recaptcha recaptchaConfig,
			Recaptcha recaptchaResponse, String action) throws CoreException {
		
		if (null == recaptchaResponse) {
			return;
		}
		
		if (!recaptchaResponse.isSuccess()) {
			throw new InvalidRecaptchaException("reCaptcha was not successfully validated");
		}
		
		if (!action.equalsIgnoreCase(recaptchaResponse.getAction())) {
			throw new InvalidRecaptchaException("reCaptcha response action does not match the required action");
		}
		
		verifyScore(recaptchaConfig, recaptchaResponse, action);
		
		log.info("reCaptcha validation success!!");
	}
	
	private void verifyScore(com.nttdata.core.context.ConfigProperties.Security.Recaptcha recaptchaConfig,
			Recaptcha recaptchaResponse, String action) throws InvalidRecaptchaException {
		
		Map<String, RecaptchaAction> customActions = recaptchaConfig.getActions();
		float scoreToApply = recaptchaConfig.getScore();
		if (null != customActions && customActions.size() > 0) {
			RecaptchaAction customAction = customActions.get(action);
			if (null != customAction && customAction.isEnabled()) {
				scoreToApply = customAction.getScore();
			} else if (null != customAction && !customAction.isEnabled()) {
				return;
			}
		}
		
		if (recaptchaResponse.getScore() < scoreToApply) {
			throw new InvalidRecaptchaException(String.format("reCaptcha response threshold (%.1f) is less than required (%.1f)", recaptchaResponse.getScore(), scoreToApply));
		}
	}

}