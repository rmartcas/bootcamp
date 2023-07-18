/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menuauthorities.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.common.utils.BatchUtils;
import com.nttdata.core.menuauthorities.mapper.MenuAuthorityMapper;
import com.nttdata.core.menuauthorities.model.MenuAuthority;
import com.nttdata.core.menuauthorities.service.MenuAuthorityService;
import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.authorities.model.Authority;

/**
 * Implementation of Menu Authority service interface to handle authorities and menu associations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class MenuAuthorityServiceImpl implements MenuAuthorityService {
	
	/** The associated mapper */
	@Autowired
	private MenuAuthorityMapper mapper;
	
	/** The SqlSessionTemplate to perform batch operations */
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * Inserts a list of authorities associated with the menu
	 * 
	 * @param dto the menu
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_menu_authorities", primaryKey = "menu_id")
	public void insertAuthorities(Menu dto) {
		List<MenuAuthority> batchList = new ArrayList<>();
		
		if (null != dto.getAuthorities() && !dto.getAuthorities().isEmpty()) {
			for (Authority authority : dto.getAuthorities()) {
				batchList.add(createMenuAuthority(dto, authority));
			}
			BatchUtils.execute(sqlSessionTemplate, MenuAuthorityMapper.class.getCanonicalName() + ".insert", batchList);
		}
	}

	
	/**
	 * Delete all authorities associated with the menu
	 * 
	 * @param dto the menu
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_menu_authorities", primaryKey = "menu_id")
	public void deleteAuthorities(Menu dto) {
		MenuAuthority ma = new MenuAuthority();
		ma.getMenu().setId(dto.getId());
		mapper.delete(ma);
	}
	
	/**
	 * Create a new {@link MenuAuthority}
	 * 
	 * @param dto the menu
	 * @param authority the authority
	 * @return {@link MenuAuthority}
	 */
	protected MenuAuthority createMenuAuthority(Menu dto, Authority authority) {
		MenuAuthority ma = new MenuAuthority();
		ma.getMenu().setId(dto.getId());
		ma.getAuthority().setId(authority.getId());
		return ma;
	}
}
