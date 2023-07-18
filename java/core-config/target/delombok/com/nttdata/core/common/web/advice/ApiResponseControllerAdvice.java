/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.web.advice;

import javax.servlet.http.HttpSession;

import org.hdiv.util.HDIVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.common.model.ApiError;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.context.ConfigProperties;

/**
 * Controller advice that handles all api responses other than {@link ApiError} and {@link ApiResponse}
 * 
 * and returns the original response object wrapped in a new {@link ApiResponse}.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestControllerAdvice
public class ApiResponseControllerAdvice implements ResponseBodyAdvice<Object> {
	
	@Autowired
	private ConfigProperties config;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		
		boolean isExclusion = false;
		String contextPath = HDIVUtil.getCurrentHttpRequest().getContextPath();
		String path = request.getURI().getPath().substring(contextPath.length());
		
		for (String exclusion : config.getApiResponse().getExclusions()) {
			if (path.matches(exclusion)) {
				isExclusion = true;
				break;
			}
		}
		
		if (body instanceof ApiError || body instanceof ApiResponse || isExclusion) {
			return body;
		}

		ApiResponse<Object> apiResponse = new ApiResponse<>(body);
		shouldRefresh(apiResponse);
		return apiResponse;
	}
	
	/**
	 * Check if the {@link ApiResponse} should notify for a config refresh.
	 * @param response {@link ApiResponse}
	 */
	protected void shouldRefresh(ApiResponse<?> response) {
		Cache cache = this.cacheManager.getCache(CacheConstants.REFRESH_CACHE);
		if (null != cache) {
			HttpSession session = HDIVUtil.getCurrentHttpRequest().getSession(false);
			// Lookup for a cache entry with last refresh time
			for (String cacheName : this.cacheManager.getCacheNames()) {
				ValueWrapper valueWrapper = cache.get(cacheName + "_" + CacheConstants.LAST_REFRESH);
				checkLastRefrestTime(session, response, valueWrapper);
			}
		}
	}
	
	/**
	 * Check if the last refresh time of the user session is previous than the last cache evict
	 * @param session {@link HttpSession} the user session to check
	 * @param response {@link ApiResponse} the response to send to the client
	 * @param valueWrapper {@link ValueWrapper} the cache entry to check
	 */
	private void checkLastRefrestTime(HttpSession session, ApiResponse<?> response, ValueWrapper valueWrapper) {
		if (null != valueWrapper) {
			// Lookup if the timestamp is after session last updated config load
			long lastRefresh = (long) session.getAttribute(CacheConstants.LAST_REFRESH);
			if((long) valueWrapper.get() >= lastRefresh) {
				response.setRefresh(true);
			}
		}
	}
}
