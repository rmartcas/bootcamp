/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.profiles.model.Profile;

/**
 * User entity
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The username.
	 * @param username {@link String} The username
	 * @return {@link String} the username
	 */
	private String username;
	
	/** 
	 * The name.
	 * @param name {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
	/** 
	 * The email.
	 * @param email {@link String} The email
	 * @return {@link String} the email
	 */
	private String email;
	
	/** 
	 * The profile.
	 * @param profile {@link Profile} The profile
	 * @return {@link Profile} the profile
	 */
	private Profile profile;
}
