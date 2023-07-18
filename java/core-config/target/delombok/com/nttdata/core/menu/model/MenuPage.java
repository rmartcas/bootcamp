// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.model;

import com.nttdata.core.common.model.Page;

/**
 * Menu search filters
 */
public class MenuPage extends Page<Menu> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MenuPage)) return false;
		final MenuPage other = (MenuPage) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		if (!super.equals(o)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MenuPage;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int result = super.hashCode();
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public MenuPage() {
	}
}
