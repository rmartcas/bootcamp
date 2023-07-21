// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.model;

import java.util.List;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.model.CrudDataLoad;

/**
 * ProfileDataLoad entity
 */
public class ProfileDataLoad extends CrudDataLoad {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The allowed authorities.
	 */
	private List<Combo> authorities;

	/**
	 * The allowed authorities.
	 * @return {@link List} of {@link Combo} the authorities
	 */
	@java.lang.SuppressWarnings("all")
	public List<Combo> getAuthorities() {
		return this.authorities;
	}

	/**
	 * The allowed authorities.
	 * @param authorities {@link List} of {@link Combo} The authorities
	 */
	@java.lang.SuppressWarnings("all")
	public void setAuthorities(final List<Combo> authorities) {
		this.authorities = authorities;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ProfileDataLoad)) return false;
		final ProfileDataLoad other = (ProfileDataLoad) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		if (!super.equals(o)) return false;
		final java.lang.Object this$authorities = this.getAuthorities();
		final java.lang.Object other$authorities = other.getAuthorities();
		if (this$authorities == null ? other$authorities != null : !this$authorities.equals(other$authorities)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ProfileDataLoad;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = super.hashCode();
		final java.lang.Object $authorities = this.getAuthorities();
		result = result * PRIME + ($authorities == null ? 43 : $authorities.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public ProfileDataLoad() {
	}
}