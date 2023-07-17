/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.context;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties("config")
@Data
public class ConfigProperties {
	
	private Locale locale;
	
	private Audit audit;
	
	private Bbdd bbdd;
	
	private Security security;
	
	private ApiResponse apiResponse;
		
	@Data
	@NoArgsConstructor
	public static final class Locale {
		private String defaultLang;
		private String paramName;
		private String[] locales;
	}
	
	@Data
	@NoArgsConstructor
	public static final class Audit {
		private boolean enabled;
		private String[] excludedMethods;
	}
	
	@Data
	@NoArgsConstructor
	public static final class Bbdd {
		
		private Pagination pagination;
		
		@Data
		@NoArgsConstructor
		public static final class Pagination {
			private String prefix;
			private String suffix;
			private String limits;
			private Integer size;
			private Integer page;
			private Integer[] pageLimits;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static final class Security {
		
		private SecurityUrl url;
		
		private SecurityHdiv hdiv;
		
		private Recaptcha recaptcha;
		
		@Data
		@NoArgsConstructor
		public static final class SecurityUrl {
			private String logout;
			private String logoutIdp;
			private String welcomePage;
		}
		
		@Data
		@NoArgsConstructor
		public static final class SecurityHdiv {
			private String errorPage;
			private String[] excludedExtensions;
			private String[] startPages;
			private String[] excludedPages;
			private List<HdivExcludedParamForUrl> excludedParamsForUrl;
			private boolean urlObfuscation;
			
			@Data
			@NoArgsConstructor
			public static final class HdivExcludedParamForUrl {
				private String[] params;
				private String url;
			}
		}
		
		@Data
		@NoArgsConstructor
		public static final class Recaptcha {
			private boolean enabled;
			private String responseHeaderName;
			private String keySite;
			private String keySecret;
			private String verifyUrl;
			private int connectionTimeout;
			private int receiveTimeout;
			private float score;
			private Map<String, RecaptchaAction> actions;
			
			@Data
			@NoArgsConstructor
			public static final class RecaptchaAction {
				private boolean enabled;
				private float score;
			}
		}
	}
	
	@Data
	@NoArgsConstructor
	public static final class ApiResponse {
		private String[] exclusions;
	}
}
