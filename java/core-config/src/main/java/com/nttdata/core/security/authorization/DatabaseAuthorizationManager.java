/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.authorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.mappings.service.MappingService;

import lombok.extern.slf4j.Slf4j;

/**
 * Allow to get the associated request mapping authorities from database.
 * If none is found in database will use spring security defined mappings.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class DatabaseAuthorizationManager implements AuthorizationManager<HttpServletRequest> {
	
	/** mappingService for authorities retrieve operations */
	private final MappingService mappingService;
	
	/** Original authorizationManager created by spring */
	private final AuthorizationManager<HttpServletRequest> authorizationManager;
	
	/** When true database defined security attributes 
	 * and spring security config attributes that meets the criteria are returned */
	private final boolean mergeAttributes;

	/**
	 * Creates a new {@link DatabaseAuthorizationManager}
	 * @param mappingService {@link MappingService} to use
	 * @param am {@link AuthorizationManager} created in spring auto configure
	 */
	public DatabaseAuthorizationManager(MappingService mappingService, AuthorizationManager<HttpServletRequest> am) {
		this(mappingService, am, false);
	}
	
	/**
	 * Creates a new {@link DatabaseAuthorizationManager} with merge attributes option
	 * @param mappingService {@link MappingService} to use
	 * @param am {@link AuthorizationManager} created in spring auto configure
	 * @param mergeAttributes {@link Boolean} Allow merge database and spring config security attributes that match the criteria
	 */
	public DatabaseAuthorizationManager(MappingService mappingService, AuthorizationManager<HttpServletRequest> am, boolean mergeAttributes) {
		this.mappingService = mappingService;
		this.authorizationManager = am;
		this.mergeAttributes = mergeAttributes;
	}
	
	private Collection<String> getPermissions(final HttpServletRequest request, boolean patterMatch) throws CoreException {
		List<Mapping> apiMappings = mappingService.getApiMappings(true);
		Collection<String> attributes = new ArrayList<>();
		for (Mapping mapping : apiMappings) {
			if(patterMatch) {
				AntPathRequestMatcher patternMatcher = new AntPathRequestMatcher(mapping.getPattern());
				if (patternMatcher.matches(request)) {
					attributes.addAll(
							mapping.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()));
				}	
			} else {
				attributes.addAll(mapping.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()));
			}
		}
		return attributes;
	}

	/**
	 * Get the required authorities from the database for the requested {@link HttpServletRequest} path <br>
	 * and evaluate against the current user authorities if is authorized to access the requested resource.
	 * 
	 * @param authentication {@link Supplier} of {@link Authentication} the current authenticated user
	 * @param request {@link HttpServletRequest} the request to authorize
	 * @return {@link AuthorizationDecision} the authorization desision. True when authorized, false otherwise.
	 */
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest request) {
		// De la lista filtrada comprobamos si el usuario tiene los authorities requeridos para acceder.
		Collection<String> permissions = new ArrayList<>();
		try {
			permissions = getPermissions(request, true);
		} catch (CoreException e) {
			log.error("Could not obtain database mapping authorities", e);
		}
		
		// Check for database authorization
		boolean hasDatabaseAuthorization = checkDatabaseAuthorization(authentication, permissions);
		boolean hasSpringAuthorization = false;
		
		//If no database attributes registerd or merge is true, get it from spring security
		if (this.mergeAttributes || permissions.isEmpty()) {
			if (permissions.isEmpty()) {
				log.debug("No configAttributes found in database, using spring security configAttributes instead.");
			}
			AuthorizationDecision authorizationDecision = this.authorizationManager.check(authentication, request);
			hasSpringAuthorization = null !=authorizationDecision && authorizationDecision.isGranted();
		}
		if (hasDatabaseAuthorization || hasSpringAuthorization) {
			return new AuthorizationDecision(true);
		}
		return new AuthorizationDecision(false);
	}
	
	/**
	 * Evaluate the current user authorities looking up if is authorized to access the requested resource
	 *
	 * @param authentication {@link Supplier} of {@link Authentication} the authenticated user.
	 * @param permissions {@link Collection} of {@link String} the authorities needed to access the resource
	 * @return {@link Boolean} true when the user is authorized, false otherwise
	 */
	private boolean checkDatabaseAuthorization(Supplier<Authentication> authentication, Collection<String> permissions) {
		Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
		for (String permission : permissions) {
			// Attempt to find a matching granted authority
			for (GrantedAuthority authority : authorities) {
				if (permission.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}
}
