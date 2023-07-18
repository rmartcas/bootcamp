/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.constants;

/**
 * Core constants for application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class CoreConstants {
	
	/** The base path for all api endpoints */
	public static final String API_BASE = "/api";


	/* Core endpoints */
	
	/** Core Controller namespace for all requests */
	public static final String CORE_CONTROLLER_NAMESPACE = API_BASE + "/core";
	
	/** Request mapping for core config */
	public static final String REQUEST_MAPPING_CONFIG = "/config";
	
	/** Path variable for language */
	public static final String PATH_VARIABLE_LANG = "lang";
	
	/** Request mapping for core locale */
	public static final String REQUEST_MAPPING_LOCALE = "/locale/{" + PATH_VARIABLE_LANG + "}";
	
	/** Error Controller namespace for all requests */
	public static final String ERROR_CONTROLLER_NAMESPACE = "/error";
	
	/** Core field id */
	public static final String FIELD_ID = "id";
	
	/** Page field filters */
	public static final String FIELD_FILTERS = "filters";
	
	/**
	 * Default constructor<br>
	 */
	private CoreConstants() {
	}
}