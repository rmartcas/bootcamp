/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import com.nttdata.core.profiles.model.Profile;

class AuthorityTest {

	@Test
	void testEqualsAndHashCode() {
		Profile red = new Profile();
		red.setName("red");
		Profile black = new Profile();
		black.setName("black");
		EqualsVerifier.forClass(Authority.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Profile.class, red, black)
			.verify();
	}
}
