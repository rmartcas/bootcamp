/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.authorities.model.Authority;

/**
 * Authority interface to handle authorities
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface AuthorityMapper extends CrudMapper<Authority> {

	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_authorities", primaryKey = "authority_id")
	void insert(Authority dto);

	@Override
	@Auditable(action = AuditableActionsEnum.UPDATE, table = "core_authorities", primaryKey = "authority_id")
	void update(Authority dto);

	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_authorities", primaryKey = "authority_id")
	void delete(Authority dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.READ, table = "core_authorities", primaryKey = "authority_id")
	Authority find(Authority dto);
}
