/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;

public class RequestIdFilter implements Filter {

	private static final String REQUEST_ID = "requestId";
	
	/**
	 * Adds a random generated id to the current request and stores the value using {@link MDC}
	 * under the key "requestId".
	 * @param request {@link ServletRequest} the request
	 * @param response {@link ServletResponse} the response
	 * @param chain {@link FilterChain} the filter chain
	 * @throws IOException in case of error
	 * @throws ServletException in case of error
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		MDC.put(REQUEST_ID, getRequestId());
		try {
			chain.doFilter(request, response);
		} finally {
			MDC.remove(REQUEST_ID);
		}
	}

	/**
	 * Create a new random request id
	 * @return {@link String} the request id
	 */
	protected String getRequestId() {
		return String.valueOf(UUID.randomUUID().toString());
	}
}

