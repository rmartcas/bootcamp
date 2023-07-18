/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.profiles.model.Profile;

/**
 * Profile interface to handle profiles
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface ProfileMapper extends CrudMapper<Profile> {

	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table="core_profiles", primaryKey="profile_id")
	void insert(Profile dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.UPDATE, table="core_profiles", primaryKey="profile_id")
	void update(Profile dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table="core_profiles", primaryKey="profile_id")
	void delete(Profile dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.READ, table = "core_profiles", primaryKey = "profile_id")
	Profile find(Profile dto);

}
