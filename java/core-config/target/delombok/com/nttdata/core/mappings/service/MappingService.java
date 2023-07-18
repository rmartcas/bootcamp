/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.service;

import java.util.List;
import java.util.Map;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.mappings.model.Mapping;

/**
 * Mapping service for application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface MappingService extends CrudService<Mapping> {

	/**
	 * Return a {@link List}&lt;{@link Mapping}&gt; containing all application security patterns and security access expressions
	 * @return  {@link List}&lt;{@link Mapping}&gt; with all application mappings and their security expression to access
	 * @throws CoreException if error
	 */
	List<Mapping> getApiMappings() throws CoreException;
	
	/**
	 * Return a {@link List}&lt;{@link Mapping}&gt; containing all application security patterns and security access expressions
	 * @param addRolePrefix {@link Boolean} when true, all authorities in the mapping list will be returned with "ROLE_" prefix plus authority name.
	 * @return  {@link List}&lt;{@link Mapping}&gt; with all application mappings and their security expression to access
	 * @throws CoreException if error
	 */
	List<Mapping> getApiMappings(boolean addRolePrefix) throws CoreException;
	
	/**
	 * Return a {@link List}&lt;{@link String}&gt; containing all authorized mapping patterns
	 * allowed by the user profile.
	 *
	 * @param user {@link CoreUser} the current authenticated user
	 * @return {@link List}&lt;{@link String}&gt; with all accesible endpoints by the user
	 * @throws CoreException if any error ocurrs during method call
	 */
	List<String> getUserMappings(CoreUser user) throws CoreException;
	
	/**
	 * Return a {@link Map}&lt;{@link String}, {@link List}&lt;{@link String}&gt;&gt; containing application paths
	 * with the profiles that can access each one.
	 *
	 * @return {@link Map}&lt;{@link String}, {@link List}&lt;{@link String}&gt;&gt; mapping access report
	 * @throws CoreException if any error ocurrs during method call
	 */
	Map<String, List<String>> validateMappings() throws CoreException;
}
