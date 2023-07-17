/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

/**
 * Test class for {@link ApplicationContextHolder}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class ApplicationContextHolderTest {

	@Mock
	private ApplicationContext appCtx;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	/**
	 * Test method getApplicationContext.
	 * Expect to be not null.
	 */
	@Test
	void testGetApplicationContext() throws Throwable {
		ApplicationContextHolder ach = new ApplicationContextHolder();
		ach.setApplicationContext(appCtx);
		
		ApplicationContext appContext = ApplicationContextHolder.getApplicationContext();
		Assertions.assertNotNull(appContext);
		Assertions.assertEquals(appCtx, appContext);
		
	}
	
	/**
	 * Test method getApplicationContext return null context if not set
	 * Expect to be  null.
	 */
	@Test
	void testGetApplicationContextReturnsNullIfNotSet() throws Throwable {
		ApplicationContext appContext = ApplicationContextHolder.getApplicationContext();
		Assertions.assertNull(appContext);
		
		ApplicationContextHolder ach = new ApplicationContextHolder();
		Assertions.assertNotNull(ach);

		appContext = ApplicationContextHolder.getApplicationContext();
		Assertions.assertNull(appContext);
	}
}