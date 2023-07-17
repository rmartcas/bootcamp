/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * Combo criteria to filter
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ComboCriteria implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public enum CriteriaOperator {
		EQUALS,
		EQUALS_IGNORE_CASE,
		NOT_EQUALS,
		GREATER,
		GREATER_OR_EQUAL,
		LESS,
		LESS_OR_EQUAL,
		CONTAINS,
		IN,
		NOT_IN,
		IS_NULL,
		IS_NOT_NULL
	}
	
	/** 
	 * The table field used to filter
	 * @param field {@link String} The table field where apply this criteria
	 * @return {@link String} the field
	 */
	@NonNull
	private String field;
	
	/** 
	 * The operator to apply in this criteria
	 * @param operator {@link CriteriaOperator} The operator
	 * @return {@link CriteriaOperator} the operator
	 */
	@NonNull
	private CriteriaOperator operator;
	
	/** 
	 * The value to compare in the table column.<br>
	 * If the selected <code>operator</code> is "IN" or "NOT_IN" this value must be a Iterable subclass of any type<br>
	 * If the selected <code>operator</code> is "IS_NULL" or "IS_NOT_NULL" this value takes no effect. Should be pass as null<br>
	 * @param value {@link Object} The value
	 * @return {@link Object} the value
	 */
	private transient Object value;
}
