/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


class UserPageTest {

	@Test
	void testEqualsAndHashCode() {
		User red = new User();
		red.setId(1L);
		User black = new User();
		EqualsVerifier.forClass(UserPage.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, red, black)
			.verify();
	}
}
