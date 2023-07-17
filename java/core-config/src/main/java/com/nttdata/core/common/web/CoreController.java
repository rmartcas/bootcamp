/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.web;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.exception.BadRequestException;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreConfig;
import com.nttdata.core.common.service.CoreService;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.core.i18n.service.I18nService;

/**
 * Core controller
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestController
@RequestMapping(CoreConstants.CORE_CONTROLLER_NAMESPACE)
class CoreController {

	/** The core. */
	@Autowired
	private CoreService coreService;
	
	/** Message source for locale translate */
	@Autowired
	private I18nService serviceI18n;
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	/**
	 * Gets the app config as response entity.
	 *
	 * @return the app config as response entity
	 */
	@GetMapping(value = CoreConstants.REQUEST_MAPPING_CONFIG, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreConfig> getConfig(HttpSession session) throws CoreException {
		CoreConfig config = coreService.getUserConfig();
		updateLastRefresh(session);
		return new ResponseEntity<>(config, HttpStatus.OK);
	}
	
	/**
	 * Set the new locale in the spring context.
	 * At this point spring has been setted the locale with the locale interceptor.
	 * 
	 * @param lang requested language to retreive literals
	 * @return Map with literals in requested lang
	 */
	@GetMapping(value = CoreConstants.REQUEST_MAPPING_LOCALE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> getTranslation(@PathVariable(CoreConstants.PATH_VARIABLE_LANG) String lang) throws CoreException  {
		Locale locale = new Locale(lang);
		this.validateLocale(locale);
		Map<String, String> i18nValues = serviceI18n.getI18n(locale);
		return new ResponseEntity<>(i18nValues, HttpStatus.ACCEPTED);
	}
	
	/**
	 * Validate the requested locale against available locales
	 *
	 * @param locale {@link Locale} the requested locale to validate
	 * @throws CoreException if not a valid locale
	 */
	protected void validateLocale(Locale locale) throws CoreException {
		if (!Arrays.asList(configProperties.getLocale().getLocales()).contains(locale.getLanguage())) {
			Errors errors = new BeanPropertyBindingResult(locale, "locale");
			errors.rejectValue("language", I18nConstants.I18N_VALIDATION_FIELD_INVALID, new Object[] {CoreConstants.PATH_VARIABLE_LANG}, null);
			throw new BadRequestException(errors);
		}
	}
	
	/**
	 * Update the user session last refresh flag with currentTimeMillis
	 *
	 * @param session {@link HttpSession} the user session
	 */
	protected void updateLastRefresh(HttpSession session) {
		session.setAttribute(CacheConstants.LAST_REFRESH, System.currentTimeMillis());
	}
}