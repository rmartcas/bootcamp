/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;
import com.nttdata.core.combos.service.ComboService;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.context.ApplicationEventPublisherHolder;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.security.events.GroupMembershipAdminTaskEvent;
import com.nttdata.core.security.events.GroupMembershipAdminTaskEvent.ActionType;
import com.nttdata.core.ui.service.UIService;
import com.nttdata.core.users.mapper.UserMapper;
import com.nttdata.core.users.model.User;
import com.nttdata.core.users.model.UserDataLoad;
import com.nttdata.core.users.service.UserService;

/**
 * User service implementation to handle users
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class UserServiceImpl implements UserService {

	/** The associated mapper */
	@Autowired
	private UserMapper userMapper;
		
	/** The combo service */
	@Autowired
	private ComboService comboService;
	
	/** The UI service */
	@Autowired
	private UIService uiService;
	
	@Override
	public CrudMapper<User> getMapper() {
		return userMapper;
	}

	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void update(User dto) {
		UserService.super.update(dto);
		
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new GroupMembershipAdminTaskEvent(dto, dto.getUsername(),
						dto.getProfile().getName(), ActionType.ASSIGN));
	}

	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void delete(User dto) {
		UserService.super.delete(dto);
	}
	
	@Override
	public DataLoad init(Page<User> dto) {
		UserDataLoad data = new UserDataLoad();
		data.setProfiles(getProfiles());
		data.setButtons(uiService.getButtons("users.init"));
		data.setColumns(uiService.getColumns("users.init"));
		return data;
	}
	
	@Override
	public DataLoad init(User dto) {
		UserDataLoad data = new UserDataLoad();
		data.setProfiles(getProfiles());
		data.setButtons(uiService.getButtons("users.initedit"));
		return data;
	}
	
	private List<Combo> getProfiles() {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		return comboService.search(combo);
	}
}