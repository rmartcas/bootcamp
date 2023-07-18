/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profileauthorities.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.common.utils.BatchUtils;
import com.nttdata.core.context.ApplicationEventPublisherHolder;
import com.nttdata.core.security.events.RoleAuthorizationAdminTaskEvent;
import com.nttdata.core.security.events.RoleAuthorizationAdminTaskEvent.ActionType;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.profiles.model.Profile;
import com.nttdata.core.profileauthorities.mapper.ProfileAuthorityMapper;
import com.nttdata.core.profileauthorities.model.ProfileAuthority;
import com.nttdata.core.profileauthorities.service.ProfileAuthorityService;

/**
 * Implementation of Profile Authority service interface to handle authorities and profile associations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class ProfileAuthorityServiceImpl implements ProfileAuthorityService {
	
	/** The associated mapper */
	@Autowired
	private ProfileAuthorityMapper profileAuthorityMapper;
	
	/** The SqlSessionTemplate to perform batch operations */
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * Inserts a list of authorities associated with the profile
	 * 
	 * @param profile the profile
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_profile_authorities", primaryKey = "profile_id")
	public void insertAuthorities(Profile profile) {
		List<ProfileAuthority> batchList = new ArrayList<>();
		
		if (null != profile.getAuthorities() && !profile.getAuthorities().isEmpty()) {
			for (Authority authority : profile.getAuthorities()) {
				batchList.add(createProfileAuthority(profile, authority));
			}
			BatchUtils.execute(sqlSessionTemplate, ProfileAuthorityMapper.class.getCanonicalName() + ".insert", batchList);
			
			for (ProfileAuthority profileAuthority : batchList) {
				registerEvent(profile, profile.getName(), profileAuthority.getAuthority().getId().toString(), ActionType.ASSIGN);
			}
		}
	}
	
	/**
	 * Inserts a list of profiles associated with the authority
	 * 
	 * @param authority the authority
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_profile_authorities", primaryKey = "authority_id")
	public void insertProfiles(Authority authority) {
		List<ProfileAuthority> batchList = new ArrayList<>();
		
		if (null != authority.getProfiles() && !authority.getProfiles().isEmpty()) {
			for (Profile profile : authority.getProfiles()) {
				batchList.add(createProfileAuthority(profile, authority));
			}
			BatchUtils.execute(sqlSessionTemplate, ProfileAuthorityMapper.class.getCanonicalName() + ".insert", batchList);
			
			for (ProfileAuthority profileAuthority : batchList) {
				registerEvent(authority, profileAuthority.getProfile().getId().toString(), authority.getName(), ActionType.ASSIGN);
			}
		}
	}
	
	/**
	 * Delete all authorities associated with the profile
	 * 
	 * @param profile the profile
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_profile_authorities", primaryKey = "profile_id")
	public void deleteAuthorities(Profile profile) {
		ProfileAuthority dto = new ProfileAuthority();
		dto.getProfile().setId(profile.getId());
		profileAuthorityMapper.deleteAuthoritiesByProfile(dto);
		
		registerEvent(profile, profile.getName(), null, ActionType.UNASSIGN);
	}
	
	/**
	 * Delete all profiles associated with the authority
	 * 
	 * @param authority the authority
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_profile_authorities", primaryKey = "authority_id")
	public void deleteProfiles(Authority authority) {
		ProfileAuthority dto = new ProfileAuthority();
		dto.getAuthority().setId(authority.getId());
		profileAuthorityMapper.deleteProfilesByAuthority(dto);
		
		registerEvent(authority, null, authority.getName(), ActionType.UNASSIGN);
	}
	
	/**
	 * Create a new {@link ProfileAuthority}
	 * 
	 * @param profile the profile
	 * @param authority the authority
	 * @return {@link ProfileAuthority}
	 */
	protected ProfileAuthority createProfileAuthority(Profile profile, Authority authority) {
		ProfileAuthority profileAuthority = new ProfileAuthority();
		profileAuthority.getProfile().setId(profile.getId());
		profileAuthority.getAuthority().setId(authority.getId());
		return profileAuthority;
	}
	
	/**
	 * Register a new {@link RoleAuthorizationAdminTaskEvent} for the requested
	 * <code>action</code>, <code>groupId</code> and <code>roleId</code>
	 * 
	 * @param source {@link Object} source event
	 * @param groupId {@link String} the profile
	 * @param roleId {@link String} the authority
	 * @param action {@link ActionType} the action
	 */
	protected void registerEvent(Object source, String groupId, String roleId, ActionType action) {
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(
				new RoleAuthorizationAdminTaskEvent(source, groupId, roleId, action));
	}
}
