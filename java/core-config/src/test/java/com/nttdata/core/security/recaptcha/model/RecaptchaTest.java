/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.model;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class RecaptchaTest {

	@Test
	void testEqualsAndHashCode() {
		Recaptcha red = new Recaptcha();
		red.setErrorCodes(new ArrayList<>());
		Recaptcha black = new Recaptcha();
		EqualsVerifier.forClass(Recaptcha.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withPrefabValues(Recaptcha.class, red, black)
			.withRedefinedSuperclass()
			.verify();
	}
}
