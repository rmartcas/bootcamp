/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.hdiv;

import org.hdiv.logs.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.nttdata.core.security.listener.SecurityEventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * Hdiv security logger extension
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class HdivSecurityLogger extends Logger {
	
	@Override
	protected void log(String type, String rule, String target, String parameterName, String parameterValue,
			String originalParameterValue, String localIp, String remoteIp, String userName,
			String validationRuleName) {
		String formatedData = format(type, target, parameterName, parameterValue,
				originalParameterValue, localIp, remoteIp, userName, validationRuleName);
		
		Marker categoryMarker = MarkerFactory.getMarker("INPUT_VALIDATION");
	    categoryMarker.add(SecurityEventListener.SECURITY_MARKER);
		log.info(categoryMarker, formatedData);
	}
}
