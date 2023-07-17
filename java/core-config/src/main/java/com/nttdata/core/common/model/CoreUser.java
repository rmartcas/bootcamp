/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common data of the current logged user
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CoreUser extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -2203985822524424051L;

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
	 * The user email.
	 * @param email {@link String} The email
	 * @return {@link String} the email
	 */
	private String email;
	
	/** 
	 * The user profile id.
	 * @param profileId {@link Long} The profileId
	 * @return {@link Long} the profileId
	 */
	private Long profileId;
	
	/** 
	 * The user authorities.
	 * @param authorities {@link Collection} The authorities
	 * @return {@link Collection} the authorities
	 */
	private Collection<String> authorities;
}
