/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


class ComboPageTest {

	@Test
	void testEqualsAndHashCode() {
		Combo red = new Combo();
		red.setId("1");
		Combo black = new Combo();
		EqualsVerifier.forClass(ComboPage.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, red, black)
			.verify();
	}
}
