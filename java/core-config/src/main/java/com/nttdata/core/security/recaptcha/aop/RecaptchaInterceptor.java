/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.aop;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.security.recaptcha.annotation.Recaptcha;
import com.nttdata.core.security.recaptcha.service.RecaptchaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor for all methods annotated with {@link Recaptcha}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class RecaptchaInterceptor implements MethodInterceptor {
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	/** The recaptcha service */
	@Autowired
	private RecaptchaService recaptchaService;
	
	/**
	 * Capture all methods or classes annotated with {@link Recaptcha} and verify that has a valid recaptcha response
	 *
	 * @param invocation {@link MethodInvocation} the join point
	 * @throws CoreException if validation fails
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (configProperties.getSecurity().getRecaptcha().isEnabled()) {
			//Check for the annotation at method level
			Recaptcha recaptcha = AnnotationUtils.findAnnotation(invocation.getMethod(), Recaptcha.class);
			if (null == recaptcha) {
				log.debug("Recaptcha annotation not found at method level, searching at class level.");
				//If not found at method level must be at class level
				recaptcha = AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Recaptcha.class);
			}
	
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String captchaResponse = request.getHeader(configProperties.getSecurity().getRecaptcha().getResponseHeaderName());
	
			String action = recaptcha != null ? recaptcha.action() : CommonConstants.EMPTY_STRING;
			recaptchaService.validate(captchaResponse, action);
		}
		return invocation.proceed();		
	}
}
