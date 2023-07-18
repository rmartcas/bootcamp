/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.i18n.service;

import java.util.Locale;
import java.util.Map;

/**
 * I18n service for message traslations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface I18nService {

	/**
	 * Gets the requested message with the current locale
	 *
	 * @param msg the message code
	 * @param args arguments to replace in msg
	 * @return String translated message
	 */
	String getTranslation(String msg, Object... args);
	
	/**
	 * Gets the requested message with selected locale
	 *
	 * @param msg the message code
	 * @param locale the requested locale to translate
	 * @param args arguments to replace in msg
	 * @return String translated message
	 */
	String getTranslation(String msg, Locale locale, Object... args);
	
	/**
	 * Create a map with all i18n key and values for requested locale
	 * @param locale {@link Locale}
	 * @return {@link Map} of strings with required key values in requested locale  
	 */
	Map<String, String> getI18n(Locale locale);
}