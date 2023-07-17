/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nttdata.bootcamp.cache.listener.CacheInitializerEventListener;

/**
 * Configuration class for spring cache
 *
 * @author NTT DATA
 * @since 0.0.1
 */
@Configuration
@EnableCaching
public class SpringCacheConfig {
	
	/**
	 * Setup cache cache initializer
	 * @return {@link CacheInitializerEventListener}
	 */
	@Bean
	public CacheInitializerEventListener cacheInitializer() {
		return new CacheInitializerEventListener();
	}
}
