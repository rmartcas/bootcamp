/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.constants;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;

/**
 * Menu constants for menu module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class MenuConstants {

	/** Controller namespace for all requests */
	public static final String CONTROLLER_NAMESPACE = CoreConstants.API_BASE + "/menus";
	
	/** Menu attribute title */
	public static final String FIELD_TITLE = "title";
	
	/** Menu attribute link */
	public static final String FIELD_LINK = "link";
	
	/** Menu attribute icon */
	public static final String FIELD_ICON = "icon";
	
	/** Menu attribute enabled */
	public static final String FIELD_ENABLED = "enabled";
	
	/** Menu attribute parent */
	public static final String FIELD_PARENT = "parent";
	
	/** Menu attribute children */
	public static final String FIELD_CHILDREN = "children";
	
	/** Menu attribute position */
	public static final String FIELD_POSITION = "position";
	
	/** Menu attribute authorities */
	public static final String FIELD_AUTHORITIES = "authorities";
	
	/** Page Menu attribute filters title */
	public static final String FIELD_FILTERS_TITLE = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_TITLE;
	
	/** Page Menu attribute filters link */
	public static final String FIELD_FILTERS_LINK = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_LINK;
	
	/** Page Menu attribute filters icon */
	public static final String FIELD_FILTERS_ICON = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_ICON;
	
	/** Page Menu attribute filters enabled */
	public static final String FIELD_FILTERS_ENABLED = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_ENABLED;
	
	/** Page Menu attribute filters parent */
	public static final String FIELD_FILTERS_PARENT = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_PARENT;
	
	/** Page Menu attribute filters children */
	public static final String FIELD_FILTERS_CHILDREN = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_CHILDREN;
	
	/** Page Menu attribute filters position */
	public static final String FIELD_FILTERS_POSITION = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_POSITION;
	
	/** Page Menu attribute filters authorities */
	public static final String FIELD_FILTERS_AUTHORITIES = CoreConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_AUTHORITIES;
	
	/**
	 * Default constructor<br>
	 */
	private MenuConstants() {
	}
}