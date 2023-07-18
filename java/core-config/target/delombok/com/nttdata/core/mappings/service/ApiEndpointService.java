/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.service;

import java.util.Map;
import com.nttdata.core.common.model.CoreUser;

/**
 * Endpoint service interface to get the user allowed endpoints
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface ApiEndpointService {
	
	/**
	 * Return a map of endpoints that current user can access by assiged authorities in user profile.
	 * <br>
	 * All returned endpoints has security enhanced by hdiv state parameter.
	 * @param user {@link CoreUser} the authenticated user
	 * @return {@link Map}&lt;{@link String}, {@link Object}&gt;
	 */
	Map<String, Object> getApiEndpoints(CoreUser user);

}
