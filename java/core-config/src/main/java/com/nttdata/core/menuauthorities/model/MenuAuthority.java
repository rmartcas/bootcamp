/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menuauthorities.model;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.authorities.model.Authority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Menu Authority entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MenuAuthority extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The menu associated.
	 * @param menu {@link Menu} The menu
	 * @return {@link Menu} the menu
	 */
	private Menu menu = new Menu();
	
	/** 
	 * The authority associated.
	 * @param authority {@link Authority} The authority
	 * @return {@link Authority} the authority
	 */
	private Authority authority = new Authority();
}
