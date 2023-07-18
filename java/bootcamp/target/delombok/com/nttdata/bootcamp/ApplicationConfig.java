/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.bootcamp;

import org.springframework.context.annotation.Import;
import com.nttdata.spring.config.*;
import com.nttdata.bootcamp.common.constants.OrderByConstants;

/**
 * bootcamp main config class.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Import({
	SpringCacheConfig.class,
	SpringContextConfig.class,
	SpringI18nConfig.class,
	SpringMvcConfig.class,
	SpringSecurityConfig.class,
	SpringHdivSecurityConfig.class,
	})
public final class ApplicationConfig {
	
	/**
	 * Constructor
	 */
    public ApplicationConfig() {
        OrderByConstants.init();
    }
}