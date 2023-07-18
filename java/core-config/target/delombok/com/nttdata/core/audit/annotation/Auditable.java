/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.nttdata.core.audit.AuditableActionsEnum;

/**
 * Auditable annotation
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD })
public @interface Auditable {

	/**
	 * Table where the audit will be persisted. Resulting table will be <code>table</code>$AUD.
	 * @return {@link String} the table
	 * @see com.nttdata.core.audit.constants.AuditableConstants#AUD_SUFFIX
	 */
	String table();
	
	/** 
	 * The primary key of the table
	 * @return {@link String} the table primary key
	 */
	String primaryKey();
	
	/**
	 * The action that will be audited
	 * @return {@link AuditableActionsEnum} the action
	 */
	AuditableActionsEnum action();
	
	/** 
	 * The index of the parameter of the object to be audited.<br>
	 * If not null must correspond to the parameter number that extends {@link com.nttdata.core.common.model.Core}
	 * @return {@link Byte} the parameter index where model is.
	 */
	byte paramIndex() default 0;
}
