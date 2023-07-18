/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.constants;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;

/**
 * Auditable constants for audit module
 */
public final class AuditableConstants {

	/** Controller namespace for all requests */
	public static final String CONTROLLER_NAMESPACE = CoreConstants.API_BASE + "/audit";
		
	/** Mapping for findByPairKey method */
	public static final String REQUEST_MAPPING_FIND_BY_PAIR_KEY = "/findByPairKey";
	
	/** table attribute name */
	public static final String FIELD_TABLE = "table";
	
	/** requestId attribute name */
	public static final String FIELD_REQUEST_ID = "requestId";
	
	/** pairKey attribute name */
	public static final String FIELD_PAIR_KEY = "pairKey";
	
	/** action attribute name */
	public static final String FIELD_ACTION = "action";
	
	/** user attribute name */
	public static final String FIELD_USER = "user";
	
	/** created attribute name */
	public static final String FIELD_CREATED = "created";
	
	/** Page Audit attribute filters table */
	public static final String FIELD_FILTERS_TABLE = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_TABLE;
	
	/** Page Audit attribute filters requestId */
	public static final String FIELD_FILTERS_REQUEST_ID = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_REQUEST_ID;
	
	/** Page Audit attribute filters pairKey */
	public static final String FIELD_FILTERS_PAIR_KEY = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_PAIR_KEY;
	
	/** Page Audit attribute filters action */
	public static final String FIELD_FILTERS_ACTION = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_ACTION;
	
	/** Page Audit attribute filters username */
	public static final String FIELD_FILTERS_USER = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_USER;
	
	/** Page Audit attribute filters created */
	public static final String FIELD_FILTERS_CREATED = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_CREATED;
	
	/** The suffix used to match audit tables */
	public static final String AUD_SUFFIX = "$AUD";

	/**
	 * Default constructor<br>
	 */
	private AuditableConstants() {
	}
}