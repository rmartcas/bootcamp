/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.util.List;
import java.util.Map;

import org.springframework.security.saml2.provider.service.authentication.DefaultSaml2AuthenticatedPrincipal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Current authenticated user data
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CoreDetails extends DefaultSaml2AuthenticatedPrincipal {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -359446569334820860L;
	
	/** 
	 * Core user with authenticated user details such name, id....
	 * @param coreUser {@link CoreUser} The coreUser
	 * @return {@link CoreUser} the coreUser
	 */
	private CoreUser coreUser;
	
	/**
	 * Create a new CoreDetails
	 * @param username the username presented to the AuthenticationProvider
	 * @param attributes the user attributes from saml
	 * @param sessionIndexes the requested session index for this user
	 */
	public CoreDetails(String username, Map<String, List<Object>> attributes,
			List<String> sessionIndexes) {
        super(username, attributes, sessionIndexes);
		this.coreUser = null;
	}
}
