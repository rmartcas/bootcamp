/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.authentication;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

/**
 * Sucess handler to setup browser locale after login
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class CoreAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /** Locale resolver to change locale */
	@Autowired
	private LocaleResolver localeResolver;
	
	/**
	 * Set the application locale and then calls 
	 * the parent class {@code handle()} method to forward or redirect to the target
	 * URL, and then calls {@code clearAuthenticationAttributes()} to remove any leftover
	 * session data.
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param authentication Authentication
	 * @throws IOException if any error ocurrs
	 * @throws ServletException if any error ocurrs
	 */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    	
    	setUserLocale(request, response);
    	super.onAuthenticationSuccess(request, response, authentication);
    }

    /**
     * Set the application locale based on spring locale session attribute or request locale
     * 
     * @param request the request
     * @param response the response
     */
	protected void setUserLocale(HttpServletRequest request, HttpServletResponse response) {
		Locale loc = Locale.class.cast(
				WebUtils.getSessionAttribute(request, org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		if (null == loc) {
			loc = RequestContextUtils.getLocale(request);
		}
		
		LocaleContextHolder.setLocale(loc);
		localeResolver.setLocale(request, response, loc);
	}
}