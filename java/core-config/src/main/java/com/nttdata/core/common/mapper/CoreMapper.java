/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.common.model.CoreUser;

/**
 * Security interface to retreive user details and roles
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface CoreMapper {

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

}
