/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.listener;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


import com.nttdata.BaseTest;
import com.nttdata.core.context.ApplicationEventPublisherHolder;
import com.nttdata.core.security.events.AdminTaskEvent;
import com.nttdata.core.security.events.CommunicationEvent;
import com.nttdata.core.security.events.FileAndResourcesEvent;
import com.nttdata.core.security.events.GroupManagementAdminTaskEvent;
import com.nttdata.core.security.events.GroupManagementAdminTaskEvent.ActionType;
import com.nttdata.core.security.events.InputValidationEvent;
import com.nttdata.core.security.events.SensitiveDataEvent;

/**
 * Security event listener test class
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class SecurityEventListenerTestIT extends BaseTest {

	private static final PreAuthenticatedAuthenticationToken AUTHENTICATION = new PreAuthenticatedAuthenticationToken("aPrincipal", "aCredentials");
	
	@SpyBean
	private SecurityEventListener listener;
	
	@Test
	void testOnApplicationAuthenticationEvent() {
		AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(AUTHENTICATION);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event);
		Mockito.verify(listener, Mockito.times(1)).onApplicationAuthenticationEvent(event);
	}
	
	@Test
	void testOnApplicationAuthorizationEvent() {
		Collection<ConfigAttribute> colecction = new ArrayList<ConfigAttribute>();
		colecction.add(new SecurityConfig("ROLE_USER"));
		
		AbstractAuthorizationEvent event = new AuthorizedEvent(this, colecction, AUTHENTICATION);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event );
		Mockito.verify(listener, Mockito.times(1)).onApplicationAuthorizationEvent(event);
	}
	
	@Test
	void testOnApplicationInputValidationEvent() {
		InputValidationEvent event = new InputValidationEvent(AUTHENTICATION);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event);
		Mockito.verify(listener, Mockito.times(1)).onApplicationInputValidationEvent(event);
	}
	
	@Test
	void testOnApplicationAdminTaskEvent() {
		AdminTaskEvent event = new GroupManagementAdminTaskEvent(AUTHENTICATION, "Administrators", ActionType.CREATE);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event);
		Mockito.verify(listener, Mockito.times(1)).onApplicationAdminTaskEvent(event);
	}
	
	@Test
	void testOnApplicationCommunicationEvent() {
		CommunicationEvent event = new CommunicationEvent(AUTHENTICATION);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event);
		Mockito.verify(listener, Mockito.times(1)).onApplicationCommunicationEvent(event);
	}
	
	@Test
	void testOnApplicationFileAndResourcesEvent() {
		FileAndResourcesEvent event = new FileAndResourcesEvent(AUTHENTICATION);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event);
		Mockito.verify(listener, Mockito.times(1)).onApplicationFileAndResourcesEvent(event);
	}
	
	@Test
	void testOnApplicationSensitiveDataEvent() {
		SensitiveDataEvent event = new SensitiveDataEvent(AUTHENTICATION);
		ApplicationEventPublisherHolder.getEventPublisher().publishEvent(event);
		Mockito.verify(listener, Mockito.times(1)).onApplicationSensitiveDataEvent(event);
	}
	
}
