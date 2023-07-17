/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.aop;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import com.nttdata.core.security.recaptcha.annotation.Recaptcha;

import lombok.EqualsAndHashCode;

/**
 * Advisor to catch {@link Recaptcha} annotation over interfaces
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode(callSuper = false)
public class RecaptchaAdvisor extends AbstractPointcutAdvisor {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** The method interceptor */
	private final transient MethodInterceptor interceptor;
	
	/** Recaptcha pointcut */
	private final transient StaticMethodMatcherPointcut pointcut = new RecaptchaAnnotationPointcut();

	/**
	 * Constructor.
	 * 
	 * @param interceptor {@link RecaptchaInterceptor}
	 */
	public RecaptchaAdvisor(RecaptchaInterceptor interceptor) {
		super();
		this.interceptor = interceptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.PointcutAdvisor#getPointcut()
	 */
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.Advisor#getAdvice()
	 */
	@Override
	public Advice getAdvice() {
		return this.interceptor;
	}

	/**
	 * A matcher that matches:
	 * <ul>
	 * <li>A method on a class annotated with {@link Recaptcha}.</li>
	 * <li>A method on a class extending another class annotated with {@link Recaptcha}.</li>
	 * <li>A method on a class implementing an interface annotated with {@link Recaptcha}.</li>
	 * <li>A method implementing a method in a interface annotated with {@link Recaptcha}.</li>
	 * </ul>
	 * <p>
	 * <strong>Note:</strong> this uses springs utils to find the annotation and
	 * will not be portable outside the spring environment.
	 * </p>
	 */
	private final class RecaptchaAnnotationPointcut extends StaticMethodMatcherPointcut {
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.aop.support.StaticMethodMatcher.matches(Method, Class<?>, Object...)
		 */
		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			final Class<Recaptcha> annotationType = Recaptcha.class;
			return null != AnnotationUtils.findAnnotation(method, annotationType)
					|| null != AnnotationUtils.findAnnotation(targetClass, annotationType);
		}
	}
}