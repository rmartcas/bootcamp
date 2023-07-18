/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.cache.constants;

/**
 * Cache constants for application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class CacheConstants {
	
	/** Application cache name */
	public  static final String APPLICATION_CACHE = "aplicacionCache";
	
	/** Core cache name */
	public static final String CORE_CACHE = "coreCache";
	
	/** Locale cache name */
	public static final String LOCALE_CACHE = "localeCache";

	/** Refresh cache name */
	public static final String REFRESH_CACHE = "refreshCache";

	/** Last resfresh flag used in user session and cache */
	public static final String LAST_REFRESH = "lastRefresh";
	
	/** Cache key for: value */
	public static final String CACHE_VALUE_KEY = "#value";
	
	/** Cache key for: locale */
	public static final String CACHE_LOCALE_KEY = "#locale";
	
	/** Cache key for: "#msg.concat(#locale)" */
	public static final String CACHE_MSG_LOCALE_KEY = "#msg.concat(" + CACHE_LOCALE_KEY + ")";
	
	
	
	/** Cache key for: #root.methodName */
	public static final String CACHE_ROOT_METHOD_NAME = "#root.methodName";
	
	/**
	 * Default constructor<br>
	 */
	private CacheConstants() {
	}
}