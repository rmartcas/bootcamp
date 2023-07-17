/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.combos.model.ComboPage;
import com.nttdata.core.combos.service.ComboService;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.context.ApplicationEventPublisherHolder;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.security.events.GroupManagementAdminTaskEvent;
import com.nttdata.core.security.events.GroupManagementAdminTaskEvent.ActionType;
import com.nttdata.core.ui.service.UIService;
import com.nttdata.core.profiles.mapper.ProfileMapper;
import com.nttdata.core.profiles.model.Profile;
import com.nttdata.core.profiles.model.ProfileDataLoad;
import com.nttdata.core.profiles.service.ProfileService;
import com.nttdata.core.profileauthorities.service.ProfileAuthorityService;

/**
 * Profile service implementation to handle profiles
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class ProfileServiceImpl implements ProfileService {

	/** The associated profile mapper */
	@Autowired
	private ProfileMapper profileMapper;
	
	/** Profile - Authority service for entity association */
	@Autowired
	private ProfileAuthorityService profileAuthorityService;
	
	/** The combo service */
	@Autowired
	private ComboService comboService;
	
	/** The UI service */
	@Autowired
	private UIService uiService;
	
	@Override
	public CrudMapper<Profile> getMapper() {
		return profileMapper;
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void insert(Profile dto) {
		// Insert the new profile
		ProfileService.super.insert(dto);
		
		// Insert all associated authorities with the new profile
		profileAuthorityService.insertAuthorities(dto);
		
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new GroupManagementAdminTaskEvent(dto, dto.getName(), ActionType.CREATE));
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void update(Profile dto) {
		// Update the profile
		ProfileService.super.update(dto);
		
		// Delete all associated auhorities
		profileAuthorityService.deleteAuthorities(dto);
		// Re Insert all associated authorities with the updated profile
		profileAuthorityService.insertAuthorities(dto);
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void delete(Profile dto) {
		// Delete all associated auhorities
		profileAuthorityService.deleteAuthorities(dto);
		
		// Delete the profile
		ProfileService.super.delete(dto);
		
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new GroupManagementAdminTaskEvent(dto, dto.getName(), ActionType.DESTROY));
	}
	
	@Override
	public DataLoad init(Page<Profile> dto) {
		ProfileDataLoad data = new ProfileDataLoad();
		data.setButtons(uiService.getButtons("profiles.init"));
		data.setColumns(uiService.getColumns("profiles.init"));
		return data;
	}
	
	@Override
	public DataLoad init(Profile dto) {
		ProfileDataLoad data = new ProfileDataLoad();
		ComboPage combo = new ComboPage();
		combo.setTable("core_authorities");
		combo.setKey("authority_id");
		combo.setValue("name");
		
		data.setAuthorities(comboService.search(combo));
		data.setButtons(uiService.getButtons("profiles.initedit"));
		return data;
	}
}