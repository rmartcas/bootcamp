/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.bootcamp.cache.listener;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.i18n.service.I18nService;
import com.nttdata.core.menu.service.MenuService;
import com.nttdata.core.profiles.model.Profile;
import com.nttdata.core.profiles.model.ProfilePage;
import com.nttdata.core.profiles.service.ProfileService;
import com.nttdata.bootcamp.common.model.ApplicationUser;

import lombok.extern.slf4j.Slf4j;

/**
 * Event listener to cache data at application startup
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class CacheInitializerEventListener {
	
	/** I18n Service for caching i18n resources */
	@Autowired
	private I18nService translateService;
	
	/** Menu service for caching user menu options and mappings */
	@Autowired
	private MenuService menuService;
	
	/** Application config properties */
	@Autowired
	private ConfigProperties configProperties;
	
	/** Menu service to get all profiles available for caching menu options by profile */
	@Autowired
	private ProfileService profileService;
	
	/**
	 * Event listener for {@link ApplicationReadyEvent} events.<br>
	 * 
	 * This method will cache all expensive operations like:
	 *  <ul>
	 * 	<li>Load all i18n resources for all available languages in the application</li>
	 * 	<li>Load all menu options by profile</li>
	 *  <li>Load all user mappings by profile</li>
	 * </ul>
	 * @param event {@link ApplicationReadyEvent} to start caching data
	 * @throws CoreException if any error
	 */
	@EventListener
	public void onApplicationReadyEvent(ApplicationReadyEvent event) throws CoreException {
		log.trace("Start preloading cache");
		
		for (String locale : configProperties.getLocale().getLocales()) {
			log.trace("Loading cache i18n for locale: {}", locale);
			translateService.getI18n(new Locale(locale));
		}
		
		Page<Profile> profilePage = new ProfilePage();
		profilePage.setSize(-1);
		profilePage = profileService.search(profilePage);
		
		CoreUser cacheUser = new ApplicationUser();
		for (Profile profile : profilePage.getRecords()) {
			log.trace("Loading cache menu for profile: {}", profile.getId());
			cacheUser.setProfileId(profile.getId());
			menuService.getUserMenu(cacheUser);
		}
		
	}
}
