/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.bootcamp.common.model;

import com.nttdata.core.common.model.CoreUser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Specific data of the current session user 
 * 
 * @author NTT DATA
 * @since 0.0.1
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ApplicationUser extends CoreUser {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

}
