/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Recaptcha annotation used in class or method.
 * When annotated the method is required to pass a successfull recaptcha validation
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Recaptcha {

	/** 
	 * The action that recaptcha should check
	 * @return {@link String} the action 
	 */
	String action();
}
