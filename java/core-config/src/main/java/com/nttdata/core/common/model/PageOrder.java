/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.io.Serializable;

import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Page order class used to sort paged queries
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageOrder implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The used property to order.
	 * @param property {@link String} The property
	 * @return {@link String} the property
	 */
	private String property;
	
	/** 
	 * The sort direction, ASC by default.
	 * @param order {@link Direction} The order
	 * @return {@link Direction} the order
	 */
	private Direction order = Direction.ASC;
	
	/** 
	 * Parse sort order as string?. Default false.
	 * @param sortAsString {@link Boolean} The sortAsString
	 * @return {@link Boolean} the sortAsString
	 */
	@JsonIgnore
	private Boolean sortAsString = Boolean.FALSE;
	
	/**
	 * PageOrder constructor using property parameter
	 * @param property The property to sort
	 */
	public PageOrder(String property) {
		super();
		this.property = property;
	}

	/**
	 * PageOrder constructor using property and asStrig parameters
	 * @param property The property to sort
	 * @param asString If this property is used as string or a column
	 */
	public PageOrder(String property, Boolean asString) {
		super();
		this.property = property;
		this.sortAsString = asString;
	}
	
	/**
	 * PageOrder constructor using property and order parameters
	 * @param property The property to sort
	 * @param order The order for this property
	 */
	public PageOrder(String property, Direction order) {
		super();
		this.property = property;
		this.order = order;
	}
}