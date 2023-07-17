/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pagination Config data for application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class PageConfig {
	
	/** 
	 * Default page number.
	 * @param page {@link Integer} The page
	 * @return {@link Integer} the page
	 */
	private int page;
	
	/** 
	 * Default number of records by page.
	 * @param size {@link Integer} The size
	 * @return {@link Integer} the size
	 */
	private int size;
	
	/** 
	 * How many records by page are allowed in selector
	 * @param pageLimits {@link Integer[]} The pageLimits
	 * @return {@link Integer[]} the pageLimits
	 */
	private Integer[] pageLimits;
}
