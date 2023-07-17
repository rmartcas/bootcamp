/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.mapper.CoreMapper;
import com.nttdata.core.common.model.CoreConfig;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.common.model.PageConfig;
import com.nttdata.core.common.model.RecaptchaConfig;
import com.nttdata.core.common.service.CoreService;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.mappings.service.ApiEndpointService;
import com.nttdata.core.menu.service.MenuService;
import com.nttdata.core.security.utils.SecurityUtils;

/**
 * Implementation of core service
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class CoreServiceImpl implements CoreService {

	/** The security mapper. */
	@Autowired
	private CoreMapper coreMapper;
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private ApiEndpointService apiService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * Gets the roles by username.
	 *
	 * @param value the value
	 * @return the roles by username
	 */
	@Override
	public List<String> getRolesByUsername(String value) {
		return coreMapper.getRolesByUsername(value);
	}

	/**
	 * Gets the user details.
	 *
	 * @param value the value
	 * @return the user details
	 */
	@Override
	public CoreUser getUserDetails(String value) {
		CoreUser coreUser = coreMapper.getUserDetails(value);
		if (null != coreUser) {
			List<String> roles = this.getRolesByUsername(value);
			coreUser.setAuthorities(roles);	
		}
		return coreUser;
	}
	
	/**
	 * Gets the application config for the current user
	 * 
	 *  @return {@link CoreConfig} with config settings
	 *  @throws CoreException in case of error
	 */
	public CoreConfig getUserConfig() throws CoreException {
		CoreConfig config = new CoreConfig();
		config.setDefaultLocale(configProperties.getLocale().getDefaultLang());
		config.setLocale(LocaleContextHolder.getLocale().getLanguage());
		config.setLocales(Arrays.asList(configProperties.getLocale().getLocales()));

		CoreUser sessionUser = SecurityUtils.getSessionUser();
		sessionUser = this.getUserDetails(sessionUser.getUsername());
		config.setUser(sessionUser);
		
		config.setApi(apiService.getApiEndpoints(sessionUser));
		config.setMenus(menuService.getUserMenu(sessionUser));
		if (configProperties.getSecurity().getRecaptcha().isEnabled()) {
			config.setRecaptcha(new RecaptchaConfig());
			config.getRecaptcha().setSiteKey(StringUtils.trimToNull(configProperties.getSecurity().getRecaptcha().getKeySite()));
			config.getRecaptcha().setHeaderName(StringUtils.trimToNull(
					configProperties.getSecurity().getRecaptcha().getResponseHeaderName()));
		}
		config.setPagination(new PageConfig());
		config.getPagination().setPage(configProperties.getBbdd().getPagination().getPage());
		config.getPagination().setSize(configProperties.getBbdd().getPagination().getSize());
		config.getPagination().setPageLimits(configProperties.getBbdd().getPagination().getPageLimits());
		return config;
	}
}
