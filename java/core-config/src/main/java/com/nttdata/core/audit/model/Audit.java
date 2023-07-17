/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.model;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.AuditableStepsEnum;
import com.nttdata.core.common.model.Core;

/**
 * Audit to handle audit data
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Audit extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The table.
	 * @param table {@link String} The table
	 * @return {@link String} the table
	 */
	private String table;
	
	/** 
	 * The auditable table primary key.
	 * @param primaryKey {@link String} The primaryKey
	 * @return {@link String} the primaryKey
	 */
	private String primaryKey;
	
	/** 
	 * The auditable action.
	 * @param action {@link AuditableActionsEnum} The action
	 * @return {@link AuditableActionsEnum} the action
	 */
	private AuditableActionsEnum action;
	
	/** 
	 * The auditable step.
	 * @param step {@link AuditableStepsEnum} The step
	 * @return {@link AuditableStepsEnum} the step
	 */
	private AuditableStepsEnum step;
	
	/** 
	 * The user performing the action to audit.
	 * @param user {@link String} The user
	 * @return {@link String} the user
	 */
	private String user;
	
	/** 
	 * The record to audit.
	 * @param data {@link Core} The record
	 * @return {@link Core} the record
	 */
	private Core<?> data;
	
	/** 
	 * Creation date of the audit.
	 * @param created {@link Date} The created
	 * @return {@link Date} the created
	 */
	private Date created;
	
	/** 
	 * Key to group pre action audit and post action audit.
	 * @param pairKey {@link String} The pairKey
	 * @return {@link String} the pairKey
	 */
	private String pairKey;
	
	/** 
	 * The request unique key as identifier to trace all audited elements in the same request.
	 * @param requestId {@link String} The requestId
	 * @return {@link String} the requestId
	 */
	private String requestId;
}
