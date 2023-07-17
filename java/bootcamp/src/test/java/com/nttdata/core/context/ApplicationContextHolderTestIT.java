/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Test class for {@link ApplicationContextHolder}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@SpringBootTest(classes = {ApplicationContextHolder.class})
class ApplicationContextHolderTestIT {

	/**
	 * Test method getApplicationContext.
	 * Expect to be not null.
	 */
	@Test
	void testGetApplicationContext() throws Throwable {
		ApplicationContext appContext = ApplicationContextHolder.getApplicationContext();
		Assertions.assertNotNull(appContext);
		
	}
}