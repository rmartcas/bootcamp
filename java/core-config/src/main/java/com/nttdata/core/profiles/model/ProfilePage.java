/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.nttdata.core.common.model.Page;

/**
 * Profiles search filters
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProfilePage extends Page<Profile> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
}
