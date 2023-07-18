/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;
import com.nttdata.core.combos.service.ComboService;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.menu.mapper.MenuMapper;
import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.menu.model.MenuDataLoad;
import com.nttdata.core.menu.service.MenuService;
import com.nttdata.core.menuauthorities.service.MenuAuthorityService;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.ui.service.UIService;

/**
 * Implementation of menu service
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class MenuServiceImpl implements MenuService {
	
	/** The associated menu mapper */
	@Autowired
	private MenuMapper mapper;
	
	/** The combo service */
	@Autowired
	private ComboService comboService;
	
	/** Menu - Authority service for entity association */
	@Autowired
	private MenuAuthorityService menuAuthorityService;
	
	/** The UI service */
	@Autowired
	private UIService uiService;
	
	@Override
	public CrudMapper<Menu> getMapper() {
		return this.mapper;
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void insert(Menu dto) {
		// Insert the new entity
		MenuService.super.insert(dto);
		
		// Insert all associated authorities with the new entity
		menuAuthorityService.insertAuthorities(dto);
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void update(Menu dto) {
		// Update the entity
		MenuService.super.update(dto);
		
		// Delete all associated auhorities
		menuAuthorityService.deleteAuthorities(dto);
		// Re Insert all associated authorities with the updated entity
		menuAuthorityService.insertAuthorities(dto);
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void delete(Menu dto) {
		// Delete all associated auhorities
		menuAuthorityService.deleteAuthorities(dto);
		
		// Delete the entity
		MenuService.super.delete(dto);
	}
	
	@Override
	public DataLoad init(Page<Menu> dto) {
		MenuDataLoad data = new MenuDataLoad();
		data.setAuthorities(getAuthorities());
		data.setButtons(uiService.getButtons("menus.init"));
		data.setColumns(uiService.getColumns("menus.init"));
		return data;
	}
	
	@Override
	public DataLoad init(Menu dto) {
		MenuDataLoad data = new MenuDataLoad();
		data.setAuthorities(getAuthorities());
		data.setParents(getParents());
		data.setButtons(uiService.getButtons("menus.initedit"));
        return data;
	}
	
	/**
	 * Find all available menu options for the user.
	 * 
	 * @param user {@link CoreUser} the user that requested its menu
	 * @return {@link List} of {@link Menu} with records
	 * @throws CoreException in case of error
	 */
	@Override
	@Cacheable(value = CacheConstants.CORE_CACHE, key = "'menus'.concat(#user.profileId)")
	public List<Menu> getUserMenu(CoreUser user) throws CoreException {
		List<Menu> userMenu = mapper.getUserMenu(user.getProfileId());
		return covertToTree(userMenu);
	}
	
	private List<Menu> covertToTree(List<Menu> userMenu) {
		List<Menu> root = new ArrayList<>();
		Map<Long, Menu> temp = userMenu.stream()
			      .collect(Collectors.toMap(Menu::getId, Function.identity()));
		
		for (Menu menu : userMenu) {
			if (null == menu.getParent() || null == menu.getParent().getId()) {
				root.add(menu);
			} else {
				Menu parent = temp.get(menu.getParent().getId());
				if (null == parent.getChildren()) {
					parent.setChildren(new ArrayList<>());
				}
				parent.getChildren().add(menu);
			}
		}
		
		setupLinks(root, CommonConstants.EMPTY_STRING);
		
		return root;
	}
	
	private void setupLinks(List<Menu> menus, String parentLink) {
		for (Menu menu : menus) {
			if (StringUtils.hasText(parentLink)) {
				menu.setLink(parentLink + CommonConstants.FORWARD_SLASH + menu.getLink());
			}

			if (null != menu.getChildren() && !menu.getChildren().isEmpty()) {
				setupLinks(menu.getChildren(), menu.getLink());
			}	
		}
	}
	
	private List<Combo> getAuthorities() {
		ComboPage combo = new ComboPage();
		combo.setTable("core_authorities");
		combo.setKey("authority_id");
		combo.setValue("name");
		return comboService.search(combo);
	}
	
	private List<Combo> getParents() {
		ComboPage combo = new ComboPage();
		combo.setTable("core_menus");
		combo.setKey("menu_id");
		combo.setValue("title");
		return comboService.search(combo);
	}
}
