/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Core dto to implement search filters of other application Dtos
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public abstract class Page<E extends Core<?>> implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Current page number.
	 * @param currentPage {@link Integer} The currentPage
	 * @return {@link Integer} the currentPage
	 */
	private int currentPage;
	
	/** 
	 * Number of records by page.
	 * @param size {@link Integer} The size
	 * @return {@link Integer} the size
	 */
	private int size;
	
	/** 
	 * The list of properties used to sort results.
	 * @param pageOrder {@link List} The pageOrder
	 * @return {@link List} of {@link PageOrder} the pageOrder
	 */
    private List<PageOrder> pageOrder;
	
    /** 
	 * Total number of pages.
	 * @param totalPages {@link Integer} The totalPages
	 * @return {@link Integer} the totalPages
	 */
	private int totalPages;
	
	/** 
	 * Total number of records.
	 * @param totalRecords {@link Integer} The totalRecords
	 * @return {@link Integer} the totalRecords
	 */
	private int totalRecords;
	
	/** 
	 * Pagination prefix for sql wrap.
	 * @param prefix {@link String} The prefix
	 * @return {@link String} the prefix
	 */
	@JsonIgnore
	private transient String prefix;
	
	/** 
	 * Pagination suffix for sql wrap.
	 * @param suffix {@link String} The suffix
	 * @return {@link String} the suffix
	 */
	@JsonIgnore
	private transient String suffix;
	
	/** 
	 * Obtained records from search.
	 * @param records {@link List} The records
	 * @return {@link List} the records
	 */
	private List<E> records = new ArrayList<>();
	
	/** 
	 * The dto for which this filter is used.
	 * @param filters The filters
	 * @return the filters
	 */
	private E filters;
}
