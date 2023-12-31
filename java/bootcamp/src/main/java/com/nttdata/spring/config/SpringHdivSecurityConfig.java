/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hdiv.config.HDIVConfig;
import org.hdiv.config.annotation.EnableHdivWebSecurity;
import org.hdiv.config.annotation.ExclusionRegistry;
import org.hdiv.config.annotation.RuleRegistry;
import org.hdiv.config.annotation.ValidationConfigurer;
import org.hdiv.config.annotation.builders.SecurityConfigBuilder;
import org.hdiv.config.annotation.configuration.HdivWebSecurityConfigurerAdapter;
import org.hdiv.filter.ValidatorHelperRequest;
import org.hdiv.logs.Logger;
import org.hdiv.urlProcessor.LinkUrlProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.context.ConfigProperties.Security.SecurityHdiv.HdivExcludedParamForUrl;
import com.nttdata.core.security.hdiv.CustomLinkUrlProcessor;
import com.nttdata.core.security.hdiv.HdivSecurityLogger;
import com.nttdata.core.security.hdiv.ValidatorHelperRequestWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class using Hdiv security
 *
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@Configuration
@EnableHdivWebSecurity
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SpringHdivSecurityConfig extends HdivWebSecurityConfigurerAdapter {
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	/** List of custom validation rules */
	private List<String> customValidations = new ArrayList<>();

	/**
	 * Configure Hdiv security
	 * @param securityConfigBuilder {@link SecurityConfigBuilder}
	 */
	@Override
	public void configure(SecurityConfigBuilder securityConfigBuilder) {
		HDIVConfig hdivConfig = securityConfigBuilder
			.errorPage(configProperties.getSecurity().getHdiv().getErrorPage())
			.reuseExistingPageInAjaxRequest(true)
			.showErrorPageOnEditableValidation(false)
            .maxPagesPerSession(10)
            .cookiesIntegrity(false)
            .urlObfuscation(configProperties.getSecurity().getHdiv().isUrlObfuscation())
			.build();
		
		hdivConfig.setExcludedExtensions(Arrays.asList(configProperties.getSecurity().getHdiv().getExcludedExtensions()));
	}
	
	/**
	 * Add exclusions to Hdiv.
	 * @param registry {@link ExclusionRegistry}
	 */
	@Override
	public void addExclusions(ExclusionRegistry registry) {
		//Start-pages
		registry.addUrlExclusions(configProperties.getSecurity().getHdiv().getStartPages());
		
		//paramsWithoutValidation
		List<HdivExcludedParamForUrl> excludedParamsForUrl = configProperties.getSecurity()
				.getHdiv().getExcludedParamsForUrl();
		if (null !=  excludedParamsForUrl && !excludedParamsForUrl.isEmpty()) {
			for (HdivExcludedParamForUrl hdivExcludedParamForUrl : excludedParamsForUrl) {
				log.trace("Excluding params << {} >> for url << {} >>",
						hdivExcludedParamForUrl.getParams(), hdivExcludedParamForUrl.getUrl());
				registry.addParamExclusions(hdivExcludedParamForUrl.getParams())
				.forUrls(hdivExcludedParamForUrl.getUrl());
			}
		}
	}
	
	/**
	 * Configure whitelist and blacklist to use in Hdiv
	 * @param registry {@link RuleRegistry}
	 */
	@Override
	public void addRules(RuleRegistry registry) {
		//acceptedPattern
		Iterator<Map.Entry<String, String>> acceptedPattern = HdivRules.ACCEPTED_PATTERNS.entrySet().iterator();
	    while (acceptedPattern.hasNext()) {
	        Map.Entry<String, String> pair = acceptedPattern.next();
	        registry.addRule(pair.getKey()).acceptedPattern(pair.getValue());
	        customValidations.add(pair.getKey());
	    }
		
		//rejectedPattern
	    Iterator<Map.Entry<String, String>> rejectedPattern = HdivRules.REJECTED_PATTERNS.entrySet().iterator();
	    while (rejectedPattern.hasNext()) {
	        Map.Entry<String, String> pair = rejectedPattern.next();
	        registry.addRule(pair.getKey()).rejectedPattern(pair.getValue());
	        customValidations.add(pair.getKey());
	    }
	    
	    //rejected mail patters
		Iterator<Map.Entry<String, String>> rejectedEmailPattern = HdivRules.REJECTED_EMAIL_PATTERNS.entrySet().iterator();
		while (rejectedEmailPattern.hasNext()) {
			Map.Entry<String, String> pair = rejectedEmailPattern.next();
			registry.addRule(pair.getKey()).rejectedPattern(pair.getValue());
			customValidations.add(pair.getKey());
		}
	}
	
	/**
	 * Configure editable valitarions.
	 * @param validationConfigurer {@link ValidationConfigurer}
	 */
	@Override
	public void configureEditableValidation(ValidationConfigurer validationConfigurer) {
		validationConfigurer.addValidation(".*").rules(customValidations.toArray(new String[0]));
	}
	
	/**
	 * Internal class to handle Hdiv custom rules
	 * 
	 * @author NTT DATA
	 * @since 0.0.1
	 */
	public static class HdivRules {
		
		/** ACCEPTED_PATTERNS **/
		public static final Map<String, String> ACCEPTED_PATTERNS;
		
		/** REJECTED_PATTERNS **/
		public static final Map<String, String> REJECTED_PATTERNS;
		
		/** REJECTED_EMAIL_PATTERNS **/
		public static final Map<String, String> REJECTED_EMAIL_PATTERNS;
		
		/**
		 * Static initialization.
		 */
		static {
			Map<String, String> acceptedPatterns = new HashMap<>();
			Map<String, String> rejectedPatterns = new HashMap<>();
			Map<String, String> rejectedEmailPatterns = new HashMap<>();
			
			acceptedPatterns.put("whiteList", "[\\w\\s(\\xE1)(\\xE9)(\\xED)(\\xF3)(\\xFA)(\\xC1)(\\xC9)(\\xCD)(\\xD3)(\\xDA)(\\xFC)(\\xDC)(\\xF1)(\\xD1)(\\xE7)(\\xC7)(\\x93)(\\x94)(\\x96)!#$%&'\"()*+,\\-./:;=?@{}\\[\\](\\xAA)(\\xBA)(\\u20AC)(\\u00A4)(\\u0080)(\\u00A3)(\\u00BF)(\\u00A1)(\\u201C)(\\u201D)]*");
			
			rejectedPatterns.put("scriptBlackList", "[\\S\\s]*(?:[<\\x3c]|(?:%3[cC]|&#(?:(?:[xX]0*3[cC])|(?:0*60))|&lt);?)(?:(?!(?:[>\\x3e](?:%3[eE]|&#(?:(?:[xX]0*3[eE])|(?:0*62))|&gt);?)).)*(?:\\s*(?:[sS\\x53\\x73]|(?:&#(?:(?:[xX]0*[57]3)|(?:0*83)|(?:0*115)));?)\\s*(?:[cC\\x43\\x63]|(?:&#(?:(?:[xX]0*[46]3)|(?:0*67)|(?:0*99)));?)\\s*(?:[rR\\x52\\x72]|(?:&#(?:(?:[xX]0*[57]2)|(?:0*82)|(?:0*114)));?)\\s*(?:[iI\\x49\\x69]|(?:&#(?:(?:[xX]0*[46]9)|(?:0*73)|(?:0*105)));?)\\s*(?:[pP\\x50\\x70]|(?:&#(?:(?:[xX]0*[57]0)|(?:0*80)|(?:0*112)));?)\\s*(?:[tT\\x54\\x74]|(?:&#(?:(?:[xX]0*[57]4)|(?:0*84)|(?:0*116)));?)(?:(?:[:\\x3a]|(?:&#(?:(?:[xX]0*3a)|(?:0*58)));?)|(?:(?:(?!(?:[>\\x3e]|(?:%3[eE]|&#(?:(?:[xX]0*3[eE])|(?:0*62)));?)).)*(?:[>\\x3e]|(?:%3[eE]|&#(?:(?:[xX]0*3[eE])|(?:0*62))|&gt);?))))[\\S\\s]*");
			rejectedPatterns.put("iframeBlackList", "[\\S\\s]*(?:[<\\x3c]|(?:%3[cC]|&#(?:(?:[xX]0*3[cC])|(?:0*60))|&lt);?)(?:(?!(?:[>\\x3e](?:%3[eE]|&#(?:(?:[xX]0*3[eE])|(?:0*62))|&gt);?)).)*(?:\\s*(?:[iI\\x49\\x69]|(?:&#(?:(?:[xX]0*[46]9)|(?:0*73)|(?:0*105)));?)\\s*(?:[fF\\x46\\x66]|(?:&#(?:(?:[xX]0*[46]6)|(?:0*70)|(?:0*102)));?)\\s*(?:[rR\\x52\\x72]|(?:&#(?:(?:[xX]0*[57]2)|(?:0*82)|(?:0*114)));?)\\s*(?:[aA\\x41\\x61]|(?:&#(?:(?:[xX]0*[46]1)|(?:0*65)|(?:0*97)));?)\\s*(?:[mM\\x4d\\x6d]|(?:&#(?:(?:[xX]0*[46][dD])|(?:0*77)|(?:0*109)));?)\\s*(?:[eE\\x45\\x65]|(?:&#(?:(?:[xX]0*[46]5)|(?:0*69)|(?:0*101)));?))[\\S\\s]*");
			rejectedPatterns.put("onBlackList", "[\\S\\s]*(?:[<\\x3c]|(?:%3[cC]|&#(?:(?:[xX]0*3[cC])|(?:0*60))|&lt);?)(?:(?!(?:[>\\x3e](?:%3[eE]|&#(?:(?:[xX]0*3[eE])|(?:0*62))|&gt);?)).)*(?:\\s*(?:[oO\\x4f\\x6f]|(?:&#(?:(?:[xX]0*[46]f)|(?:0*79)|(?:0*111)));?)\\s*(?:[nN\\x4e\\x6e]|(?:&#(?:(?:[xX]0*[46]e)|(?:0*78)|(?:0*110)));?)[\\w&#;]*\\s*(?:=|(?:%3[dD]|&#(?:(?:[xX]0*3[dD])|(?:0*61)));?))[\\S\\s]*");
			
			rejectedEmailPatterns.put("emailBlackList", "\\S*(?:\\s*(?:[sS\\x53\\x73]|(?:&amp;#(?:(?:[xX]0*[57]3)|(?:0*83)|(?:0*115)));?)\\s*(?:[cC\\x43\\x63]|(?:&amp;#(?:(?:[xX]0*[46]3)|(?:0*67)|(?:0*99)));?)\\s*(?:[rR\\x52\\x72]|(?:&amp;#(?:(?:[xX]0*[57]2)|(?:0*82)|(?:0*114)));?)\\s*(?:[iI\\x49\\x69]|(?:&amp;#(?:(?:[xX]0*[46]9)|(?:0*73)|(?:0*105)));?)\\s*(?:[pP\\x50\\x70]|(?:&amp;#(?:(?:[xX]0*[57]0)|(?:0*80)|(?:0*112)));?)\\s*(?:[tT\\x54\\x74]|(?:&amp;#(?:(?:[xX]0*[57]4)|(?:0*84)|(?:0*116)));?))\\S*]]#");
			
			ACCEPTED_PATTERNS = Collections.unmodifiableMap(acceptedPatterns);
			REJECTED_PATTERNS = Collections.unmodifiableMap(rejectedPatterns);
			REJECTED_EMAIL_PATTERNS = Collections.unmodifiableMap(rejectedEmailPatterns);
		}
		
		/**
		 * Constructor.
		 */
		private HdivRules() {
			
		}
	}
	
	/**
	 * Bean post processor class to replace default hdiv implementations
	 * 
	 * @author NTT DATA
	 * @since 0.0.1
	 */
	class HdivBeanPostProccesor implements BeanPostProcessor {
		
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
			if (bean instanceof Logger) {
				return new HdivSecurityLogger();
			}
			
			if (bean instanceof ValidatorHelperRequest) {
				try {
					return new ValidatorHelperRequestWrapper((ValidatorHelperRequest) bean);
				} catch (Exception e) {
					log.error("Could not replace default ValidatorHelperRequest");
				}
			}
			
			if (bean instanceof LinkUrlProcessor) {
				return new CustomLinkUrlProcessor((LinkUrlProcessor) bean);
			}
			
			return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
		}
	}
	
	@Bean
	public CustomLinkUrlProcessor customLinkUrlProcessor(HDIVConfig config) {
		CustomLinkUrlProcessor processor = new CustomLinkUrlProcessor();
		processor.setConfig(config);
		return processor;
	}
	
	@Bean
	public BeanPostProcessor hdivBeanPostProccesor() {
		return new HdivBeanPostProccesor();
	}
}
