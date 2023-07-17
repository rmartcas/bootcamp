/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.ui.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;

import com.nttdata.BaseTest;
import com.nttdata.core.common.model.Button;
import com.nttdata.core.common.model.CoreDetails;
import com.nttdata.core.ui.service.UIService;
import com.nttdata.bootcamp.common.model.ApplicationUser;

class UIServiceImplTestIT extends BaseTest {
	
	/** test object */
	@Autowired
	private UIService service;
	
	@Test
	void testGetButtonsWithValidModuleNameReturnsNotEmptyList() throws Exception {
		CoreDetails details = new CoreDetails("", new HashMap<>(), new ArrayList<>() );
		details.setCoreUser(new ApplicationUser());
		Collection<String> auths = new ArrayList<>();
		auths.add("ROLE_TEST");
		details.getCoreUser().setAuthorities(auths);
		Saml2Authentication samlAuth = new Saml2Authentication(details, "resp", new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(samlAuth);
		
		List<Button> list = service.getButtons("test.ui.buttons");
		Assertions.assertNotNull(list);
		Assertions.assertFalse(list.isEmpty());
		Assertions.assertEquals(3, list.size());
	}
	
	@Test
	void testGetButtonsWithValidRoleReturnsNotEmptyList() throws Exception {
		CoreDetails details = new CoreDetails("", new HashMap<>(), new ArrayList<>() );
		details.setCoreUser(new ApplicationUser());
		Collection<String> auths = new ArrayList<>();
		auths.add("ROLE_TEST_2");
		details.getCoreUser().setAuthorities(auths);
		Saml2Authentication samlAuth = new Saml2Authentication(details, "resp", new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(samlAuth);
		
		List<Button> list = service.getButtons("test.ui.buttons");
		Assertions.assertNotNull(list);
		Assertions.assertFalse(list.isEmpty());
		Assertions.assertEquals(1, list.size());
	}
	
	@Test
	void testGetButtonsWithInvalidRoleReturnsEmptyList() throws Exception {
		CoreDetails details = new CoreDetails("", new HashMap<>(), new ArrayList<>() );
		details.setCoreUser(new ApplicationUser());
		Collection<String> auths = new ArrayList<>();
		details.getCoreUser().setAuthorities(auths);
		Saml2Authentication samlAuth = new Saml2Authentication(details, "resp", new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(samlAuth);
		
		List<Button> list = service.getButtons("test.ui.buttons");
		Assertions.assertNotNull(list);
		Assertions.assertTrue(list.isEmpty());
	}
}