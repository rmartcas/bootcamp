/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.i18n.service.impl;

import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.core.i18n.service.I18nService;

class I18nServiceImplTest {
	
	@Mock
	private MessageSource messageSource;
	
	/** test object */
	@InjectMocks
	private I18nService service = new I18nServiceImpl();
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testWhenGetTranslationWithExistingKeyThenNotEmptyString() throws Exception {
		Mockito.when(
				messageSource.getMessage(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(Locale.class)))
				.thenReturn("Not Found: the requested resource could not be found");
		String msg = service.getTranslation(I18nConstants.I18N_ERROR_PREFIX + "404");
		Assertions.assertEquals("Not Found: the requested resource could not be found", msg);
	}
	
	@Test
	void testWhenGetTranslationWithoutExistingKeyThenEmptyString() throws Exception {
		Mockito.when(
				messageSource.getMessage(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(Locale.class)))
				.thenThrow(new NoSuchMessageException("Key not found"));
		String msg = service.getTranslation("i18n.not.existing.key");
		Assertions.assertEquals("", msg);
	}
}