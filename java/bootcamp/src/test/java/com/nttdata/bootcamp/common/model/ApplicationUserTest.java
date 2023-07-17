package com.nttdata.bootcamp.common.model;

import org.junit.jupiter.api.Test;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class ApplicationUserTest {

	@Test
	void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ApplicationUser.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.verify();
	}
}
