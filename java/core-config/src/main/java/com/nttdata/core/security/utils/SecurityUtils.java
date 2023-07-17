/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.utils;

import java.lang.reflect.Field;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nttdata.core.common.model.CoreDetails;
import com.nttdata.core.common.model.CoreUser;

import lombok.extern.slf4j.Slf4j;

/**
 * Util class to obtain spring security authenticated user data
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public final class SecurityUtils {

	/** Private constructor */
	private SecurityUtils() {
	}
	
	/**
	 * Return the {@link CoreUser} in current authenticated user if any.
	 * @return {@link CoreUser} current session user data or null if not authenticated
	 */
	public static CoreUser getSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			return ((CoreDetails) authentication.getPrincipal()).getCoreUser();
		}
		return null;
	}
	
	/**
	 * Return a field value using reflection.
	 * <br>
	 *
	 * @param <T> Any object
	 * 
	 * @param target {@link Object} the object from witch obtain the field value
	 * @param fieldName {@link String} the field to obtain
	 * @return T. The field value if any or null. If any error is raised during the evaluation, null will be returned too.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object target, String fieldName) {
		Field field = org.springframework.util.ReflectionUtils.findField(target.getClass(), fieldName);
		
		if (field != null) {
			try {
				field.trySetAccessible();
				return (T) field.get(target);
			} catch (Exception e) {
				log.error("Could not get the field {} value", field);
			}
		}
		return null;
	}
}