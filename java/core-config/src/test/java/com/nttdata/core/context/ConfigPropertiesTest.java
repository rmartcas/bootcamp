/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.context;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class ConfigPropertiesTest {

	@Test
	void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ConfigProperties.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeLocale() {
		EqualsVerifier.forClass(ConfigProperties.Locale.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeAudit() {
		EqualsVerifier.forClass(ConfigProperties.Audit.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeBbdd() {
		EqualsVerifier.forClass(ConfigProperties.Bbdd.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeBbddPagination() {
		EqualsVerifier.forClass(ConfigProperties.Bbdd.Pagination.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeSecurity() {
		EqualsVerifier.forClass(ConfigProperties.Security.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeSecurityUrl() {
		EqualsVerifier.forClass(ConfigProperties.Security.SecurityUrl.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testEqualsAndHashCodeSecurityHdiv() {
		EqualsVerifier.forClass(ConfigProperties.Security.SecurityHdiv.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
}
