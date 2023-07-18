/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.constants;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;

/**
 * Authority constants for authorities module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class AuthorityConstants {

	/** Controller namespace for all requests */
	public static final String CONTROLLER_NAMESPACE = CoreConstants.API_BASE + "/authorities";
	
	/** Authority attribute name */
	public static final String FIELD_NAME = "name";
	
	/** Authority attribute description */
	public static final String FIELD_DESCRIPTION = "description";
	
	/** Authority attribute profiles */
	public static final String FIELD_PROFILES = "profiles";
	
	/** Page Authority attribute filters name */
	public static final String FIELD_FILTERS_NAME = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_NAME;
	
	/** Page Authority attribute filters description */
	public static final String FIELD_FILTERS_DESCRIPTION = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_DESCRIPTION;
	
	/**
	 * Default constructor<br>
	 */
	private AuthorityConstants() {
	}
}