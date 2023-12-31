/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.authentication;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.saml2.provider.service.authentication.DefaultSaml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.util.Assert;

import com.nttdata.core.common.model.CoreDetails;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.common.service.CoreService;

import lombok.Setter;

/**
 * An authentication provider wrapper that encapsulates the main authentication provider
 * and transform the resulting authentication object to a {@link Saml2Authentication}
 * 
 * @author NTT DATA
 * @since 0.0.1
 *
 */
@Setter
public class AuthenticationProviderWrapper implements AuthenticationProvider, InitializingBean {
	
	/** 
	 * The {@link AuthenticationProvider} that this class wraps.
	 * @param provider {@link AuthenticationProvider} The provider
	 */
	private AuthenticationProvider provider;
	
	/** 
	 * The user details service to use after successfull authentication.
	 * @param userDetailService {@link CoreService} The userDetailService
	 */
	@Autowired
	private CoreService userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		Authentication result = provider.authenticate(authentication);
		
		Saml2Authentication resultSaml = (Saml2Authentication) result;
		DefaultSaml2AuthenticatedPrincipal oldSaml2Principal = (DefaultSaml2AuthenticatedPrincipal) resultSaml.getPrincipal();
		
		CoreUser coreUser = userDetailService.getUserDetails(oldSaml2Principal.getName());
		if (null == coreUser) {
			throw new BadCredentialsException("Bad credentials");
		}

		CoreDetails saml2Principal = new CoreDetails(oldSaml2Principal.getName(),
				oldSaml2Principal.getAttributes(), oldSaml2Principal.getSessionIndexes());
		
		saml2Principal.setRelyingPartyRegistrationId(oldSaml2Principal.getRelyingPartyRegistrationId());
		saml2Principal.setCoreUser(coreUser);
		
		Collection<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(
				coreUser.getAuthorities().toArray(new String[coreUser.getAuthorities().size()]));
		
		Saml2Authentication samlAuth = new Saml2Authentication(saml2Principal, resultSaml.getSaml2Response(), grantedAuthorities);
		samlAuth.setDetails(result.getDetails());
		
		return samlAuth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return provider.supports(authentication);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(provider, "Provider must not be null");
	}
}
