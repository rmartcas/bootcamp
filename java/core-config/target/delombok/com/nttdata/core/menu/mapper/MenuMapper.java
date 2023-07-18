/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.menu.model.Menu;

/**
 * Menu interface to manage application menus and user allowed endpoints
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface MenuMapper extends CrudMapper<Menu>{
	
	/**
	 * Find all available menu options for the user.
	 * 
	 * @param profileId {@link Long} the current profile of authenticated user
	 * @return {@link List} of {@link Menu} with records
	 */
	List<Menu> getUserMenu(@Param("profileId") Long profileId);
	
	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_menus", primaryKey = "menu_id")
	void insert(Menu dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.UPDATE, table = "core_menus", primaryKey = "menu_id")
	void update(Menu dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_menus", primaryKey = "menu_id")
	void delete(Menu dto);
	
	@Override
	@Auditable(action = AuditableActionsEnum.READ, table = "core_menus", primaryKey = "menu_id")
	Menu find(Menu dto);
}
