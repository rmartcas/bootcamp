/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class ApiResponseTest {

	@Test
	void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ApiResponse.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withPrefabValues(String.class, "One", "Two")
			.withRedefinedSuperclass()
			.verify();
	}
}

