/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.aop;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class AuditableAdvisorTest {

	@Test
	void testEqualsAndHashCode() {
		EqualsVerifier.forClass(AuditableAdvisor.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS, Warning.ALL_FIELDS_SHOULD_BE_USED)
			.withRedefinedSuperclass()
			.verify();
	}
}
