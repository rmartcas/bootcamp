/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.listener;

import ch.qos.logback.classic.ClassicConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.security.events.AdminTaskEvent;
import com.nttdata.core.security.events.CommunicationEvent;
import com.nttdata.core.security.events.FileAndResourcesEvent;
import com.nttdata.core.security.events.InputValidationEvent;
import com.nttdata.core.security.events.SensitiveDataEvent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Event listener to log security events
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class SecurityEventListener {
	
	/** Generic marker for all security logs */
	public static final Marker SECURITY_MARKER = MarkerFactory.getMarker(CommonConstants.SECURITY);
	
	enum SecurityCategories {
		ADMIN_TASK("Origin: {}, User: {}, Task type: {}, Details: {}"),
		AUTHENTICATION("Origin: {}, User: {}, Application: {}, Result: {}"),
		AUTHORIZATION("Origin: {}, User: {}, Application: {}, Resource: {}, Result: {}"),
		COMMUNICATIONS_SECURITY("Origin: {}, Target URL: {}, Result: {}"),
		FILES_AND_RESOURCES("User: {}, Document: {}, Information: {}"),
		INPUT_VALIDATION("Origin: {}, User: {}, Request: {}, Result: {}"),
		SENSITIVE_DATA("Origin: {}, User: {}, Repository: {}, Request: {}");
		
		@Getter
		private final String logInfo;
		
		private SecurityCategories(String logInfo) {
			this.logInfo = logInfo;
		}
	}

	/**
	 * Event listener for {@link AuthenticationSuccessEvent} events
	 * @param event {@link AuthenticationSuccessEvent} to log
	 */
	@EventListener
	public void onApplicationAuthenticationEvent(AuthenticationSuccessEvent event) {
		logEvent(SecurityCategories.AUTHENTICATION, getRemoteIP(),
				event.getAuthentication().getName(), getRequestUri(), "Authenticated session");
	}
	
	/**
	 * Event listener for {@link AuthenticationFailureExpiredEvent} events
	 * @param event {@link AuthenticationFailureExpiredEvent} to log
	 */
	@EventListener
	public void onApplicationAuthenticationEvent(AuthenticationFailureExpiredEvent event) {
		logEvent(SecurityCategories.AUTHENTICATION, getRemoteIP(),
				event.getAuthentication().getName(), getRequestUri(), "Expired ID");
	}
	
	/**
	 * Event listener for {@link AuthenticationFailureBadCredentialsEvent} events
	 * @param event {@link AuthenticationFailureBadCredentialsEvent} to log
	 */
	@EventListener
	public void onApplicationAuthenticationEvent(AuthenticationFailureBadCredentialsEvent event) {
		logEvent(SecurityCategories.AUTHENTICATION, getRemoteIP(),
				event.getAuthentication().getName(), getRequestUri(), "Invalid credentials");
	}
	
	/**
	 * Event listener for {@link AbstractAuthorizationEvent} events
	 * @param event {@link AbstractAuthorizationEvent} to log
	 */
	@EventListener
	public void onApplicationAuthorizationEvent(AbstractAuthorizationEvent event) {
		String result = CommonConstants.OK;
		if (event instanceof AuthenticationCredentialsNotFoundEvent 
				|| event instanceof AuthorizationFailureEvent) {
			result = CommonConstants.KO;
		}
		logEvent(SecurityCategories.AUTHORIZATION, getRemoteIP(), getUser(),
				CommonConstants.EMPTY_STRING, getRequestUri(), result);
	}
	
	/**
	 * Event listener for {@link InputValidationEvent} events
	 * @param event {@link InputValidationEvent} to log
	 */
	@EventListener
	public void onApplicationInputValidationEvent(InputValidationEvent event) {
		logEvent(SecurityCategories.INPUT_VALIDATION, getRemoteIP(), 
				getUser(), getRequestUri(), CommonConstants.KO);
	}
	
	/**
	 * Event listener for {@link AdminTaskEvent} events
	 * @param event {@link AdminTaskEvent} to log
	 */
	@EventListener
	public void onApplicationAdminTaskEvent(AdminTaskEvent event) {
		logEvent(SecurityCategories.ADMIN_TASK, getRemoteIP(),
				getUser(), event.getTaskType(), event.getDetails());
	}
	
	/**
	 * Event listener for {@link CommunicationEvent} events
	 * @param event {@link CommunicationEvent} to log
	 */
	@EventListener
	public void onApplicationCommunicationEvent(CommunicationEvent event) {
		logEvent(SecurityCategories.COMMUNICATIONS_SECURITY,
				getRemoteIP(), event.getSource().toString(), CommonConstants.KO);
	}
	
	/**
	 * Event listener for {@link FileAndResourcesEvent} events
	 * @param event {@link FileAndResourcesEvent} to log
	 */
	@EventListener
	public void onApplicationFileAndResourcesEvent(FileAndResourcesEvent event) {
		logEvent(SecurityCategories.FILES_AND_RESOURCES,
				getUser(), event.getSource().toString());
	}
	
	/**
	 * Event listener for {@link SensitiveDataEvent} events
	 * @param event {@link SensitiveDataEvent} to log
	 */
	@EventListener
	public void onApplicationSensitiveDataEvent(SensitiveDataEvent event) {
		logEvent(SecurityCategories.SENSITIVE_DATA, getRemoteIP(), getUser(),
				event.getSource().toString(), getRequestUri());
	}
	
	/**
	 * Log the event info under requested category with specified params
	 * @param category {@link SecurityCategories} to log
	 * @param params {@link Object} to log
	 */
	private void logEvent(SecurityCategories category, Object... params) {
		if (log.isInfoEnabled()) {
			Marker categoryMarker = MarkerFactory.getMarker(category.toString());
			categoryMarker.add(SECURITY_MARKER);
			log.info(categoryMarker, category.getLogInfo(), params);
		}
	}
	
	protected String getRemoteIP() {
		String remoteIP = MDC.get(ClassicConstants.REQUEST_X_FORWARDED_FOR);		
		if(CommonConstants.EMPTY_STRING.equals(StringUtils.trimToEmpty(remoteIP))) {
			remoteIP = MDC.get(ClassicConstants.REQUEST_REMOTE_HOST_MDC_KEY);
		}

		return StringUtils.trimToEmpty(remoteIP);
	}
	
	protected String getUser() {
		String user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			user = authentication.getName();
		} 
		return StringUtils.defaultString(user, "anonymousUser");
	}
	
	protected String getRequestUri() {
		String uri = MDC.get(ClassicConstants.REQUEST_REQUEST_URI);
		String query = MDC.get(ClassicConstants.REQUEST_QUERY_STRING);
		if(!CommonConstants.EMPTY_STRING.equals(StringUtils.trimToEmpty(query))) {
			uri += "?" + query;
		}

		return StringUtils.trimToEmpty(uri);
	}
}
