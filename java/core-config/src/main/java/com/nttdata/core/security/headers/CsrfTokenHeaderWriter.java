/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.headers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.header.HeaderWriter;

/**
 * Csrf header writer to send the csrf token in each request as a response header
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class CsrfTokenHeaderWriter implements HeaderWriter {

	/** The token repository */
	private final CsrfTokenRepository tokenRepository;
	
	/**
	 * Creates a new instance of {@link CsrfTokenHeaderWriter}
	 * @param tokenRepository {@link CsrfTokenRepository} repository to use 
	 */
	public CsrfTokenHeaderWriter(CsrfTokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	/**
	 * Write the X-CSRF-TOKEN header to the response.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		CsrfToken token = tokenRepository.loadToken(request);
		
		if (null != token) {
			String headerName = token.getHeaderName();
			if (!response.containsHeader(headerName)) {
				response.setHeader(headerName, token.getToken());
			}			
		}
	}
}