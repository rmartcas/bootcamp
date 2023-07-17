/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test application loads
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class ApplicationTestIT extends BaseTest {
	
	/**
	 * Test context load
	 */
	@Test
	void testMain() {
		Application.main(new String[] {});
		Assertions.assertTrue(true);
	}
}