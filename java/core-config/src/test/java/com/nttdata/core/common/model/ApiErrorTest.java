/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import com.nttdata.core.common.constants.CommonConstants;

class ApiErrorTest {

	@Test
	void testEqualsAndHashCode() {
		EqualsVerifier.forClass(ApiError.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withPrefabValues(Pair.class, Pair.of(CommonConstants.OK, new ArrayList<>()), Pair.of(CommonConstants.KO, new ArrayList<>()))
			.withRedefinedSuperclass()
			.verify();
	}
	
	@Test
	void testWhenStringErrorIsPresentThenListOfPairErrorsIsCreated() {
		ApiError apiError = new ApiError(HttpStatus.OK, "Test message", "Error 1,", "Error 2", "Error n");
		Assertions.assertNotNull(apiError.getErrors());
		Assertions.assertEquals(3, apiError.getErrors().size());
	}
}
