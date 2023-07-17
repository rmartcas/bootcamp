/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.common.model.Page;

class InitializableTest {
	
	private Initializable<Core<?>> sut = null;

	/**
	 * Initialize sut object
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	void initMockWs() throws Exception {
		this.sut = new Initializable<Core<?>>() {
		};
	}
	
	@Test
	void testInitWithCore() {
		Assertions.assertNull(this.sut.init(new CoreUser()));
	}
	
	@Test
	void testInitWithPage() {
		Assertions.assertNull(this.sut.init(new Page<Core<?>>() {

			/** serialVersionUID */
			private static final long serialVersionUID = 3812344598856922211L;
		}));
	}

}
