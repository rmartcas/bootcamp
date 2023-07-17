/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.common.constants.CommonConstants;

import lombok.EqualsAndHashCode;

/**
 * AuditableAdvisor to catch {@link Auditable} annotation over interfaces
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode(callSuper = false)
public class AuditableAdvisor extends AbstractPointcutAdvisor {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** The method interceptor */
	private final transient MethodInterceptor interceptor;
	
	/** Auditable pointcut */
	private final transient StaticMethodMatcherPointcut pointcut;

	/**
	 * Constructor.
	 * @param auditableInterceptor {@link AuditableInterceptor} the interceptor to use
	 * @param excluded {@link String} array of excluded classes and methods to be audited.
	 */
	public AuditableAdvisor(AuditableInterceptor auditableInterceptor, String... excluded) {
		super();
		this.interceptor = auditableInterceptor;
		this.pointcut = new AuditAnnotationPointcut(excluded);
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
	 * <li>A method on a class annotated with {@link Auditable}.</li>
	 * <li>A method on a class extending another class annotated with {@link Auditable}.</li>
	 * <li>A method on a class implementing an interface annotated with {@link Auditable}.</li>
	 * <li>A method implementing a method in a interface annotated with {@link Auditable}.</li>
	 * </ul>
	 * <p>
	 * <strong>Note:</strong> this uses springs utils to find the annotation and
	 * will not be portable outside the spring environment.
	 * </p>
	 */
	private final class AuditAnnotationPointcut extends StaticMethodMatcherPointcut {
		
		/**
		 * List of canonical classes and methods having {@link Auditable} annotation that will be excluded from being audited.<br>
		 * Ej: 
		 * <ul>
		 * <li>com.foo.Foo.helloFoo</li>
		 * <li>com.foo.Bar.doSomething</li>
		 * </ul>
		 */
		private final List<String> excludedMethods;
		
		public AuditAnnotationPointcut(String... methods) {
			super();
			this.excludedMethods = Arrays.asList(methods);
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.aop.support.StaticMethodMatcher.matches(Method, Class<?>, Object...)
		 */
		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			return null != AnnotationUtils.findAnnotation(method, Auditable.class) && !isExcluded(method, targetClass);
		}
		
		private boolean isExcluded(Method method, Class<?> targetClass) {
			final String canonicalMethod = (targetClass.getName() + CommonConstants.DOT + method.getName()).toLowerCase();
			boolean methodMatch = false;
			for (String m : excludedMethods) {
				methodMatch = canonicalMethod.equals(m);
				if (methodMatch) {
					break;
				}
			}
			return methodMatch;
		}
	}
}