/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nttdata.core.menu.model.Menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Config data for application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CoreConfig {
	
	/** 
	 * Current locale.
	 * @param locale {@link String} The locale
	 * @return {@link String} the locale
	 */
	private String locale;
	
	/** 
	 * Default locale.
	 * @param defaultLocale {@link String} The defaultLocale
	 * @return {@link String} the defaultLocale
	 */
	private String defaultLocale;
	
	/** 
	 * List of available locales.
	 * @param locales {@link List} The locales
	 * @return {@link List} the locales
	 */
	private List<String> locales;
	
	/** 
	 * The current authenticated user.
	 * @param user {@link CoreUser} The user
	 * @return {@link CoreUser} the user
	 */
	private CoreUser user;
	
	/** 
	 * Map of api endpoints. Filled with all api modules the user has access to.
	 * @param api {@link Map} The api
	 * @return {@link Map} the api
	 */
	private Map<String, Object> api;
	
	/** 
	 * List of available menus.
	 * @param menus {@link List} The menus
	 * @return {@link List} the menus
	 */
	private List<Menu> menus;
	
	/** 
	 * The recaptcha config.
	 * @param recaptcha {@link RecaptchaConfig} The recaptcha config to use
	 * @return {@link RecaptchaConfig} the recaptcha config to use
	 */
	private RecaptchaConfig recaptcha;
	
	/** 
	 * The pagination default settings.
	 * @param pagination {@link PageConfig} The pagination
	 * @return {@link PageConfig} the pagination
	 */
	private PageConfig pagination;
}
