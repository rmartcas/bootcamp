/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.nttdata.core.cache.constants.CacheConstants;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class CacheEvictAspect {
	
	@Autowired
	private CacheManager cacheManager;

	@After("@annotation(org.springframework.cache.annotation.CacheEvict)")
	public void methodCacheEvit(JoinPoint proceedingJoinPoint) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		// Get intercepted method details
		CacheEvict annotation = AnnotationUtils.getAnnotation(methodSignature.getMethod(), CacheEvict.class);
		if (null != annotation) {
			for (String cacheName : annotation.cacheNames()) {
				// Update the evicted cache last refresh timestamp
				Cache cache = this.cacheManager.getCache(CacheConstants.REFRESH_CACHE);
				if (null != cache) {
					log.debug("Update last refresh time for cache {}.", cacheName);
					cache.put(cacheName + "_" + CacheConstants.LAST_REFRESH, System.currentTimeMillis());
				}
			}
		}
	}
}
