/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.constants;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;

/**
 * Mapping constants for mapping module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class MappingConstants {

	/** Controller namespace for all requests */
	public static final String CONTROLLER_NAMESPACE = CoreConstants.API_BASE + "/mappings";
	
	/** Controller mapping for validate report */
	public static final String MAPPING_VALIDATE =  "/validate";
	
	/** Mapping attribute pattern */
	public static final String FIELD_PATTERN = "pattern";
	
	/** Mapping attribute position */
	public static final String FIELD_POSITION = "position";
	
	/** Mapping attribute authorities */
	public static final String FIELD_AUTHORITIES = "authorities";
	
	/** Page Mapping attribute filters pattern */
	public static final String FIELD_FILTERS_PATTERN = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_PATTERN;
	
	/** Page Mapping attribute filters position */
	public static final String FIELD_FILTERS_POSITION = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_POSITION;
	
	/** Page Mapping attribute filters authorities */
	public static final String FIELD_FILTERS_AUTHORITIES = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_AUTHORITIES;
	
	/**
	 * Default constructor<br>
	 */
	private MappingConstants() {
	}
}