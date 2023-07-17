/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


class ProfilePageTest {

	@Test
	void testEqualsAndHashCode() {
		Profile red = new Profile();
		red.setId(1L);
		Profile black = new Profile();
		EqualsVerifier.forClass(ProfilePage.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, red, black)
			.verify();
	}
}
