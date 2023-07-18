/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.mappings.model.Mapping;

/**
 * Mapping interface to manage application security mappings
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface MappingMapper extends CrudMapper<Mapping> {

	/**
	 * Return a {@link List}&lt;{@link Mapping}&gt; containing all application security patterns and security access expression
	 * @return  {@link List}&lt;{@link Mapping}&gt; with all application mappings and their security expression to access
	 */
	List<Mapping> getApiMappings();
	
	/**
	 * Return a {@link List}&lt;{@link String}&gt; containing all authorized mapping patterns
	 * allowed by the user profile.
	 *
	 * @param profileId {@link Long} the current profile of authenticated user
	 * @return {@link List}&lt;{@link String}&gt; with all accesible endpoints by the user
	 */
	List<String> getUserMappings(Long profileId);
	
	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_mappings", primaryKey = "mapping_id")
	void insert(Mapping dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.UPDATE, table = "core_mappings", primaryKey = "mapping_id")
	void update(Mapping dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_mappings", primaryKey = "mapping_id")
	void delete(Mapping dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.READ, table = "core_mappings", primaryKey = "mapping_id")
	Mapping find(Mapping dto);

	/**
	 * Return a {@link List}&lt;{@link String}&gt; containing all profiles that match the request authorties
	 *
	 * @param ids {@link Set} with the authorities to check
	 * @return {@link List}&lt;{@link String}&gt; with all profiles that match the request authorties
	 */
	List<String> getProfilesByAuthorities(Set<Long> ids);
}
