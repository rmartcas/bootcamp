/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config;

import java.util.List;
import java.util.Locale;

import org.hdiv.listener.InitListener;
import org.hdiv.web.multipart.HdivStandardServletMultipartResolver;
import org.hdiv.web.validator.EditableParameterValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.interceptor.CoreLocaleChangeInterceptor;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.security.interceptor.HdivValidationErrorInterceptor;
import com.nttdata.spring.config.constants.SpringConfigConstants;

/**
 * Configuration class for spring mvc
 *
 * @author NTT DATA
 * @since 0.0.1
 */
@Configuration
@ComponentScan(
		basePackages = SpringConfigConstants.COMPONENT_SCAN_BASE_PACKAGE,
		includeFilters = @Filter(value = Controller.class),
		excludeFilters = {
				//Excludes own config classes
				@Filter(pattern = SpringConfigConstants.COMPONENT_SCAN_FILTER, type = FilterType.REGEX),
				//Exclude service and repository classes
                @Filter(value = Service.class),
                @Filter(value = Repository.class)
		}
	)
public class SpringMvcConfig implements WebMvcConfigurer {
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 * Hdiv generic validator
	 * @return {@link EditableParameterValidator}
	 */
	@Override
	public Validator getValidator() {
		return new EditableParameterValidator();
	}
	
	/**
	 * Setup file upload for application using Hdiv multipart resolver.
	 * 
	 * @return {@link MultipartResolver}
	 */
	@Bean(name = SpringConfigConstants.Multipart.BEAN_NAME)
	public MultipartResolver multipartResolver() {
		return new HdivStandardServletMultipartResolver();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern(SpringConfigConstants.PATTERN_ALL)) {
			registry
				.addResourceHandler(SpringConfigConstants.PATTERN_ALL)
				.addResourceLocations(SpringConfigConstants.Mvc.CLASSPATH_RESOURCE_LOCATIONS.toArray(new String[0]))
				.setCachePeriod(3600)
			    .resourceChain(true)
			    .addResolver(new EncodedResourceResolver())
			    .addResolver(new PathResourceResolver());
		}
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		CoreLocaleChangeInterceptor localeChangeInterceptor = new CoreLocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(configProperties.getLocale().getParamName());
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns(CoreConstants.CORE_CONTROLLER_NAMESPACE + SpringConfigConstants.PATTERN_ALL);
		
		registry.addInterceptor(new HdivValidationErrorInterceptor());
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", configProperties.getSecurity().getUrl().getWelcomePage()).setContextRelative(false);
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (HttpMessageConverter<?> httpMessageConverter : converters) {
			if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
				((MappingJackson2HttpMessageConverter) httpMessageConverter).setObjectMapper(mapper);
			}
		}
	}

	/** 
	 * Initializes a new {@link InitListener} 
	 * @return {@link InitListener}
	 */
	@Bean
	public InitListener initListener() {
		return new InitListener();
	}
	
    /**
     * Setup the application locale
     * @param configProperties {@link ConfigProperties} the config properties
     * @return {@link LocaleResolver} for application
     */
    @Bean(name = SpringConfigConstants.I18n.LOCALE_RESOLVER_BEAN_NAME)
    public LocaleResolver localeResolver(ConfigProperties configProperties) {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(configProperties.getLocale().getDefaultLang()));
        return localeResolver;
    }
}
