/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.logger;

import org.hdiv.filter.ValidatorError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HdivSecurityLoggerTest {
	
	private HdivSecurityLogger sut;

	/**
	 * Initialize sut object
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	void initMockWs() throws Exception {
		this.sut = new HdivSecurityLogger();
	}

	@Test
	void testLog() {
		HdivSecurityLogger sutSpy = Mockito.spy(sut);
		
		ValidatorError error = new ValidatorError("type", "rule", "target", "parameterName", "parameterValue",
				"originalParameterValue", "localIp", "remoteIp", "userName", "validationRuleName");
		sutSpy.log(error);
		
		Mockito.verify(sutSpy, Mockito.times(1)).log("type", "rule", "target", "parameterName", "parameterValue",
				"originalParameterValue", "localIp", "remoteIp", "userName", "validationRuleName");
	}

}
