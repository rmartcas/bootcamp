// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.model;

import java.util.List;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.model.CrudDataLoad;

/**
 * AuthorityDataLoad entity
 */
public class AuthorityDataLoad extends CrudDataLoad {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The allowed profiles.
	 */
	private List<Combo> profiles;

	/**
	 * The allowed profiles.
	 * @return {@link List} of {@link Combo} the profiles
	 */
	@java.lang.SuppressWarnings("all")
	public List<Combo> getProfiles() {
		return this.profiles;
	}

	/**
	 * The allowed profiles.
	 * @param profiles {@link List} of {@link Combo} The profiles
	 */
	@java.lang.SuppressWarnings("all")
	public void setProfiles(final List<Combo> profiles) {
		this.profiles = profiles;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof AuthorityDataLoad)) return false;
		final AuthorityDataLoad other = (AuthorityDataLoad) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		if (!super.equals(o)) return false;
		final java.lang.Object this$profiles = this.getProfiles();
		final java.lang.Object other$profiles = other.getProfiles();
		if (this$profiles == null ? other$profiles != null : !this$profiles.equals(other$profiles)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof AuthorityDataLoad;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = super.hashCode();
		final java.lang.Object $profiles = this.getProfiles();
		result = result * PRIME + ($profiles == null ? 43 : $profiles.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public AuthorityDataLoad() {
	}
}
