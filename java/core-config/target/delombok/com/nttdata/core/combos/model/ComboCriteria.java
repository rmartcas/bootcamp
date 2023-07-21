// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.model;

import java.io.Serializable;
import lombok.NonNull;

/**
 * Combo criteria to filter
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class ComboCriteria implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	public enum CriteriaOperator {
		EQUALS, EQUALS_IGNORE_CASE, NOT_EQUALS, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, CONTAINS, IN, NOT_IN, IS_NULL, IS_NOT_NULL;
	}

	/**
	 * The table field used to filter
	 */
	@NonNull
	private String field;
	/**
	 * The operator to apply in this criteria
	 */
	@NonNull
	private CriteriaOperator operator;
	/**
	 * The value to compare in the table column.<br>
	 * If the selected <code>operator</code> is "IN" or "NOT_IN" this value must be a Iterable subclass of any type<br>
	 * If the selected <code>operator</code> is "IS_NULL" or "IS_NOT_NULL" this value takes no effect. Should be pass as null<br>
	 */
	private transient Object value;

	/**
	 * The table field used to filter
	 * @return {@link String} the field
	 */
	@NonNull
	@java.lang.SuppressWarnings("all")
	public String getField() {
		return this.field;
	}

	/**
	 * The operator to apply in this criteria
	 * @return {@link CriteriaOperator} the operator
	 */
	@NonNull
	@java.lang.SuppressWarnings("all")
	public CriteriaOperator getOperator() {
		return this.operator;
	}

	/**
	 * The value to compare in the table column.<br>
	 * If the selected <code>operator</code> is "IN" or "NOT_IN" this value must be a Iterable subclass of any type<br>
	 * If the selected <code>operator</code> is "IS_NULL" or "IS_NOT_NULL" this value takes no effect. Should be pass as null<br>
	 * @return {@link Object} the value
	 */
	@java.lang.SuppressWarnings("all")
	public Object getValue() {
		return this.value;
	}

	/**
	 * The table field used to filter
	 * @param field {@link String} The table field where apply this criteria
	 */
	@java.lang.SuppressWarnings("all")
	public void setField(@NonNull final String field) {
		if (field == null) {
			throw new java.lang.NullPointerException("field is marked non-null but is null");
		}
		this.field = field;
	}

	/**
	 * The operator to apply in this criteria
	 * @param operator {@link CriteriaOperator} The operator
	 */
	@java.lang.SuppressWarnings("all")
	public void setOperator(@NonNull final CriteriaOperator operator) {
		if (operator == null) {
			throw new java.lang.NullPointerException("operator is marked non-null but is null");
		}
		this.operator = operator;
	}

	/**
	 * The value to compare in the table column.<br>
	 * If the selected <code>operator</code> is "IN" or "NOT_IN" this value must be a Iterable subclass of any type<br>
	 * If the selected <code>operator</code> is "IS_NULL" or "IS_NOT_NULL" this value takes no effect. Should be pass as null<br>
	 * @param value {@link Object} The value
	 */
	@java.lang.SuppressWarnings("all")
	public void setValue(final Object value) {
		this.value = value;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ComboCriteria)) return false;
		final ComboCriteria other = (ComboCriteria) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$field = this.getField();
		final java.lang.Object other$field = other.getField();
		if (this$field == null ? other$field != null : !this$field.equals(other$field)) return false;
		final java.lang.Object this$operator = this.getOperator();
		final java.lang.Object other$operator = other.getOperator();
		if (this$operator == null ? other$operator != null : !this$operator.equals(other$operator)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ComboCriteria;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $field = this.getField();
		result = result * PRIME + ($field == null ? 43 : $field.hashCode());
		final java.lang.Object $operator = this.getOperator();
		result = result * PRIME + ($operator == null ? 43 : $operator.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public ComboCriteria(@NonNull final String field, @NonNull final CriteriaOperator operator, final Object value) {
		if (field == null) {
			throw new java.lang.NullPointerException("field is marked non-null but is null");
		}
		if (operator == null) {
			throw new java.lang.NullPointerException("operator is marked non-null but is null");
		}
		this.field = field;
		this.operator = operator;
		this.value = value;
	}
}