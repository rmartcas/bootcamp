/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.authorities.model.Authority;

/**
 * Menu entity
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Menu extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The menu title. Should be a i18n key.
	 * @param title {@link String} The title
	 * @return {@link String} the title
	 */
	private String title;
	
	/** 
	 * The path where menu should navigate.
	 * @param link {@link String} The link
	 * @return {@link String} the link
	 */
	private String link;
	
	/** 
	 * The icon of this menu. Optional.
	 * @param icon {@link String} The icon
	 * @return {@link String} the icon
	 */
	private String icon;
	
	/** 
	 * The position of this menu. Optional.
	 * @param position {@link Byte} The position
	 * @return {@link Byte} the position
	 */
	private Byte position;
	
	/** 
	 * List of authorities witch can access this menu. If list is empty no one can access this menu.
	 * @param roles {@link List} The roles
	 * @return {@link List} the roles
	 */
	private List<String> roles;
	
	/** 
	 * List of authorities witch can access this menu. If list is empty no one can access this menu.
	 * @param authorities {@link List} The authorities
	 * @return {@link List} the authorities
	 */
	private List<Authority> authorities;
	
	/** 
	 * Is this menu enabled to navigate?.
	 * @param enabled {@link Boolean} The enabled
	 * @return {@link Boolean} the enabled
	 */
	private boolean enabled;
	
	/** 
	 * The parent menu reference if any.
	 * @param parent {@link Menu} The parent
	 * @return {@link Menu} the parent
	 */
	private Menu parent;
	
	/** 
	 * List of menus that depends on this.
	 * @param children {@link List} The children
	 * @return {@link List} the children
	 */
	private List<Menu> children;
	
}
