/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.service.impl;

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
import com.nttdata.core.security.events.RoleManagementAdminTaskEvent;
import com.nttdata.core.security.events.RoleManagementAdminTaskEvent.ActionType;
import com.nttdata.core.ui.service.UIService;
import com.nttdata.core.authorities.mapper.AuthorityMapper;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.authorities.model.AuthorityDataLoad;
import com.nttdata.core.authorities.service.AuthorityService;
import com.nttdata.core.profileauthorities.service.ProfileAuthorityService;


/**
 * Authority service implementation to handle authorities
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

	/** The associated mapper */
	@Autowired
	private AuthorityMapper authorityMapper;
	
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
	public CrudMapper<Authority> getMapper() {
		return authorityMapper;
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void insert(Authority dto) {
		// Insert the new authority
		AuthorityService.super.insert(dto);
		
		// Insert all associated profiles with the new authority
		profileAuthorityService.insertProfiles(dto);

		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new RoleManagementAdminTaskEvent(dto, dto.getName(), ActionType.CREATE, false));
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void update(Authority dto) {
		// Update the authority
		AuthorityService.super.update(dto);

		// Delete all associated profiles
		profileAuthorityService.deleteProfiles(dto);
		// Re Insert all associated profiles with the updated authority
		profileAuthorityService.insertProfiles(dto);
		
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new RoleManagementAdminTaskEvent(dto, dto.getName(), ActionType.MODIFY, false));
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void delete(Authority dto) {
		// Delete all associated profiles
		profileAuthorityService.deleteProfiles(dto);

		// Delete the authority
		AuthorityService.super.delete(dto);
		
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new RoleManagementAdminTaskEvent(dto, dto.getName(), ActionType.DELETE, false));
	}
	
	@Override
	public DataLoad init(Page<Authority> dto) {
		AuthorityDataLoad data = new AuthorityDataLoad();
		data.setButtons(uiService.getButtons("authorities.init"));
		data.setColumns(uiService.getColumns("authorities.init"));
		return data; 
	}
	
	@Override
	public DataLoad init(Authority dto) {
		AuthorityDataLoad data = new AuthorityDataLoad();
		
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		
		data.setProfiles(comboService.search(combo));
		data.setButtons(uiService.getButtons("authorities.initedit"));
		return data; 
	}
}