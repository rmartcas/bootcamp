/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.ui.service.impl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nttdata.core.common.model.Button;
import com.nttdata.core.common.model.TableColumn;
import com.nttdata.core.ui.service.UIService;

class UIServiceImplTest {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	/** test object */
	private UIService service;
	
	@BeforeEach
	void setup() {
		service = new UIServiceImpl();
		ReflectionTestUtils.setField(service, "objectMapper", objectMapper);
	}
	
	@Test
	void testGetColumnsWithValidModuleNameReturnsNotEmptyList() throws Exception {
		List<TableColumn> list = service.getColumns("test.ui.columns");
		Assertions.assertNotNull(list);
		Assertions.assertFalse(list.isEmpty());
	}
	
	@Test
	void testGetColumnsWithInvalidModuleNameReturnsEmptyList() throws Exception {
		List<TableColumn> list = service.getColumns("test.ui.columns.not.found");
		Assertions.assertNotNull(list);
		Assertions.assertTrue(list.isEmpty());
	}
	
	@Test
	void testGetColumnsWithNullModuleNameReturnsEmptyList() throws Exception {
		List<TableColumn> list = service.getColumns(null);
		Assertions.assertNotNull(list);
		Assertions.assertTrue(list.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = {"test.ui.buttons", "test.ui.buttons.not.found", ""})
	void testGetButtons(String arg) throws Exception {
		List<Button> list = service.getButtons(arg);
		Assertions.assertNotNull(list);
		Assertions.assertTrue(list.isEmpty());
	}
}