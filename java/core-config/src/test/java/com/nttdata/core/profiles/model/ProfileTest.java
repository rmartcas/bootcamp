/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import com.nttdata.core.authorities.model.Authority;

class ProfileTest {

	@Test
	void testEqualsAndHashCode() {
		Authority red = new Authority();
		red.setName("red");
		Authority black = new Authority();
		black.setName("black");
		EqualsVerifier.forClass(Profile.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Authority.class, red, black)
			.verify();
	}
}
