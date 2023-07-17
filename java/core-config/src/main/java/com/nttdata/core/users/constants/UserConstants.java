/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.constants;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;

/**
 * User constants for users module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class UserConstants {

	/** Controller namespace for all requests */
	public static final String CONTROLLER_NAMESPACE = CoreConstants.API_BASE + "/users";

	/** User attribute username */
	public static final String FIELD_USERNAME = "username";
	
	/** User attribute name */
	public static final String FIELD_NAME = "name";
	
	/** User attribute email */
	public static final String FIELD_EMAIL = "email";
		
	/** User attribute profile */
	public static final String FIELD_PROFILE = "profile";
	
	/** Page User attribute filters username */
	public static final String FIELD_FILTERS_USERNAME = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_USERNAME;
	
	/** Page User attribute filters name */
	public static final String FIELD_FILTERS_NAME = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_NAME;
	
	/** Page User attribute filters email */
	public static final String FIELD_FILTERS_EMAIL = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_EMAIL;
	
	/** Page User attribute filters profile */
	public static final String FIELD_FILTERS_PROFILE = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_PROFILE;
	
	/**
	 * Default constructor<br>
	 */
	private UserConstants() {
	}
}