/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config;

import java.time.Duration;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.nttdata.core.audit.aop.AuditableAdvisor;
import com.nttdata.core.audit.aop.AuditableInterceptor;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.interceptor.PaginationInterceptor;
import com.nttdata.core.common.utils.EscapedStringSerializer;
import com.nttdata.core.context.ApplicationContextHolder;
import com.nttdata.core.context.ApplicationEventPublisherHolder;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.security.recaptcha.aop.RecaptchaAdvisor;
import com.nttdata.core.security.recaptcha.aop.RecaptchaInterceptor;
import com.nttdata.spring.config.constants.SpringConfigConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class for spring context
 *
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@Configuration
@ComponentScan(
		basePackages = SpringConfigConstants.COMPONENT_SCAN_BASE_PACKAGE, 
		excludeFilters = {
				//Excludes all controllers
				@Filter(value = Controller.class),
                @Filter(value = ControllerAdvice.class),
				//Excludes own config classes
				@Filter(pattern = SpringConfigConstants.COMPONENT_SCAN_FILTER, type = FilterType.REGEX)}
	)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringContextConfig {
	
	/**
	 * Instantiates new {@link ApplicationContextHolder}
	 * @return {@link ApplicationContextHolder}
	 */
	@Bean
	public ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"audit.enabled"}, havingValue="true", matchIfMissing = true)
	public AuditableAdvisor auditableAdvisor(ConfigProperties configProperties) {
		log.debug("Creating auditable advisor");
		
		String[] excludedMethods = configProperties.getAudit().getExcludedMethods();
		if (null == excludedMethods) {
			excludedMethods = new String[]{CommonConstants.EMPTY_STRING};
		}
		return new AuditableAdvisor(auditableInterceptor(), excludedMethods);
	}
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"audit.enabled"}, havingValue="true", matchIfMissing = true)
	public AuditableInterceptor auditableInterceptor() {
		log.debug("Creating auditable interceptor");
		return new AuditableInterceptor();
	}
	
	/**
	 * Instantiates new {@link ApplicationEventPublisherHolder}
	 * @return {@link ApplicationEventPublisherHolder}
	 */
	@Bean
	public ApplicationEventPublisherHolder applicationEventPublisherHolder() {
		return new ApplicationEventPublisherHolder();
	}
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"security.recaptcha.enabled"}, havingValue="true", matchIfMissing = false)
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, ConfigProperties configProperties) {
		return restTemplateBuilder
				.setConnectTimeout(Duration.ofMillis(configProperties.getSecurity().getRecaptcha().getConnectionTimeout()))
				.setReadTimeout(Duration.ofMillis(configProperties.getSecurity().getRecaptcha().getReceiveTimeout()))
				.build();
	}
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"security.recaptcha.enabled"}, havingValue="true", matchIfMissing = false)
	public RecaptchaAdvisor recaptchaAdvisor() {
		log.debug("Creating recaptcha advisor");
		return new RecaptchaAdvisor(recaptchaInterceptor());
	}
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"security.recaptcha.enabled"}, havingValue="true", matchIfMissing = false)
	public RecaptchaInterceptor recaptchaInterceptor() {
		log.debug("Creating recaptcha interceptor");
		return new RecaptchaInterceptor();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
		mapper.setSerializationInclusion(Include.NON_NULL);
		SimpleModule module = new SimpleModule();
		module.addSerializer(String.class, new EscapedStringSerializer());
		mapper.registerModule(module);
		return mapper;
	}
	
	@Bean
	public Interceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}
