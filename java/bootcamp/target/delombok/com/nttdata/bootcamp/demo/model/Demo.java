// Generated by delombok at Tue Jul 18 14:38:05 CEST 2023
package com.nttdata.bootcamp.demo.model;

import com.nttdata.core.common.model.Core;

public class Demo extends Core<String> {
	private static final long serialVersionUID = 1L;
	private String atributo1;

	@java.lang.SuppressWarnings("all")
	public Demo() {
	}

	@java.lang.SuppressWarnings("all")
	public String getAtributo1() {
		return this.atributo1;
	}

	@java.lang.SuppressWarnings("all")
	public void setAtributo1(final String atributo1) {
		this.atributo1 = atributo1;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof Demo)) return false;
		final Demo other = (Demo) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$atributo1 = this.getAtributo1();
		final java.lang.Object other$atributo1 = other.getAtributo1();
		if (this$atributo1 == null ? other$atributo1 != null : !this$atributo1.equals(other$atributo1)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof Demo;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $atributo1 = this.getAtributo1();
		result = result * PRIME + ($atributo1 == null ? 43 : $atributo1.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "Demo(atributo1=" + this.getAtributo1() + ")";
	}
}
