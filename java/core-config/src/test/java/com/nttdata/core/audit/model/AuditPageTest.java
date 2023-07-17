/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class AuditPageTest {

	@Test
	void testEqualsAndHashCode() {
		Audit red = new Audit();
		red.setId(1L);
		Audit black = new Audit();
		EqualsVerifier.forClass(AuditPage.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, red, black)
			.verify();
	}
}
