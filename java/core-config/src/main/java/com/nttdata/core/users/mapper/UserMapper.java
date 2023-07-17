/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.users.model.User;

/**
 * User interface to handle users
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface UserMapper extends CrudMapper<User> {

	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table="core_users", primaryKey="user_id")
	void insert(User dto);

	@Override
	@Auditable(action = AuditableActionsEnum.UPDATE, table="core_users", primaryKey="user_id")
	void update(User dto);

	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table="core_users", primaryKey="user_id")
	void delete(User dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.READ, table="core_users", primaryKey="user_id")
	User find(User dto);
}
