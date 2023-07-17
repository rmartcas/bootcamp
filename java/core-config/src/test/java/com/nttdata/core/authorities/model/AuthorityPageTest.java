/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


class AuthorityPageTest {

	@Test
	void testEqualsAndHashCode() {
		Authority red = new Authority();
		red.setId(1L);
		Authority black = new Authority();
		EqualsVerifier.forClass(AuthorityPage.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, red, black)
			.verify();
	}
}
