/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.i18n.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.i18n.service.I18nService;

/**
 * Implementation of i18n service
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@Service
public class I18nServiceImpl implements I18nService {

	/** The message source */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Gets the requested message with the current locale
	 *
	 * @param msg the message code
	 * @param args arguments to replace in msg
	 * @return String translated message
	 */
	@Override
	public String getTranslation(String msg, Object... args) {
		return getTranslation(msg, LocaleContextHolder.getLocale(), args);
	}
	
	/**
	 * Gets the requested message with selected locale
	 *
	 * @param msg the message code
	 * @param locale the requested locale to translate
	 * @param args arguments to replace in msg
	 * @return String translated message
	 */
	@Cacheable(value = CacheConstants.LOCALE_CACHE, key = CacheConstants.CACHE_MSG_LOCALE_KEY)
	@Override
	public String getTranslation(String msg, Locale locale, Object... args) {
		try {
			return messageSource.getMessage(msg, args, locale);
		} catch (NoSuchMessageException e) {
			log.trace("Message not found.", e);
			return CommonConstants.EMPTY_STRING;
		}
	}
	
	/**
	 * Create a map with all i18n key and values for requested locale
	 * 
	 * @param locale {@link Locale}
	 * @return {@link Map} of strings with required key values in requested locale  
	 */
	@Cacheable(value = CacheConstants.LOCALE_CACHE, key = CacheConstants.CACHE_LOCALE_KEY)
	@Override
	public Map<String, String> getI18n(Locale locale) {
		Map<String, String> i18n = new HashMap<>();
		Set<String> basenameSet = ((ResourceBundleMessageSource) messageSource).getBasenameSet();
		
		for (String basename : basenameSet) {
			ResourceBundle labels = ResourceBundle.getBundle(basename, locale);
			Set<String> keySet = labels.keySet();
			
			for (String key : keySet) {
				String value = labels.getString(key);
				if (log.isTraceEnabled()) {
					log.trace("Loading i18n key-value from {} for locale {}: {} = {}", basename, locale.getLanguage(), key, value);
				}
				i18n.put(key, value);
			}
		}
		return i18n;
	}
}