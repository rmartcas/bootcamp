/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Spring constants for config application
 */
public final class SpringConfigConstants {

	/** Base package for spring scanning */
	public static final String COMPONENT_SCAN_BASE_PACKAGE = "com.nttdata";
	
	/** Spring filter scan */
	public static final String COMPONENT_SCAN_FILTER = "com\\.nttdata\\.spring\\..*";
	
	
	public static final String PATTERN_ALL = "/**";
	
	

	/**
	 * Default constructor<br>
	 */
	private SpringConfigConstants() {
	}
	
	public static final class Transaction {
		
		public static final String BEAN_NAME = "txManager";
		
		private Transaction() {
		}
	}
	
	public static final class Multipart {
		
		public static final String BEAN_NAME = "multipartResolver";
		
		private Multipart() {
		}
	}
	
	public static final class Mvc {
		
		public static final List<String> CLASSPATH_RESOURCE_LOCATIONS = Collections.unmodifiableList(
				Arrays.asList(
			"classpath:/META-INF/resources/",
		    "classpath:/resources/",
		    "classpath:/static/",
		    "classpath:/public/",
            "/static/build/"
		));
		
		private Mvc() {
		}
	}
	
	public static final class I18n {
		
		public static final String MESSAGE_SOURCE_BEAN_NAME = "messageSource";
		
		public static final String LOCALE_RESOLVER_BEAN_NAME = "localeResolver";
		
		public static final String PROPERTIES_CONFIG_LOCATION = "classpath*:i18n/**/*.properties";
		
		public static final String PROPERTIES_EXTENSION = ".properties";
		
		private I18n() {
		}
	}
	
	public static final class Security {
        public static final String ACTUATOR_ROLE = "ACTUATOR";
        public static final String ACTUATOR_USERNAME_ENV = "ACTUATOR_USER";
        public static final String ACTUATOR_PASSWORD_ENV = "ACTUATOR_PASSWORD";
		private Security() {
		}
	}

}