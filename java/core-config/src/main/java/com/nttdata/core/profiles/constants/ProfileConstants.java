/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.constants;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;

/**
 * Profile constants for profiles module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class ProfileConstants {

	/** Controller namespace for all requests */
	public static final String CONTROLLER_NAMESPACE =  CoreConstants.API_BASE + "/profiles";
	
	/** Profile attribute name */
	public static final String FIELD_NAME = "name";
	
	/** Profile attribute description */
	public static final String FIELD_DESCRIPTION = "description";
	
	/** Profile attribute authorities */
	public static final String FIELD_AUTHORITIES = "authorities";
	
	/** Page profile attribute filters name */
	public static final String FIELD_FILTERS_NAME = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_NAME;
	
	/** Page profile attribute filters description */
	public static final String FIELD_FILTERS_DESCRIPTION = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_DESCRIPTION;
	
	/**
	 * Default constructor<br>
	 */
	private ProfileConstants() {
	}
}