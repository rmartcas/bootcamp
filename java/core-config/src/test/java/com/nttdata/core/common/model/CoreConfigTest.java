/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.authorities.model.Authority;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class CoreConfigTest {

	@Test
	void testEqualsAndHashCode() {
		Authority red = new Authority();
		red.setName("red");
		Authority black = new Authority();
		black.setName("black");
		
		Menu redMenu = new Menu();
		redMenu.setId(1L);
		redMenu.setChildren(new ArrayList<>());
		Menu blackMenu = new Menu();
		
		EqualsVerifier.forClass(CoreConfig.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Authority.class, red, black)
			.withPrefabValues(Menu.class, redMenu, blackMenu)
			.verify();
	}
}
