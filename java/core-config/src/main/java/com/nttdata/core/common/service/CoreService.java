/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.service;

import java.util.List;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreConfig;
import com.nttdata.core.common.model.CoreUser;

/**
 * Core service of application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface CoreService {
	
	/**
	 * Gets the roles by username.
	 *
	 * @param value the value
	 * @return the roles by username
	 */
	List<String> getRolesByUsername(String value);

	/**
	 * Gets the user details.
	 *
	 * @param value the value
	 * @return the user details
	 */
	CoreUser getUserDetails(String value);
	
	/**
	 * Gets the application config for the current user
	 * 
	 * @return {@link CoreConfig} with config settings
	 * @throws CoreException in case of error
	 */
	CoreConfig getUserConfig() throws CoreException;
}
