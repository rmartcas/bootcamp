// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.model;

import java.util.Date;
import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.AuditableStepsEnum;
import com.nttdata.core.common.model.Core;

/**
 * Audit to handle audit data
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class Audit extends Core<Long> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The table.
	 */
	private String table;
	/**
	 * The auditable table primary key.
	 */
	private String primaryKey;
	/**
	 * The auditable action.
	 */
	private AuditableActionsEnum action;
	/**
	 * The auditable step.
	 */
	private AuditableStepsEnum step;
	/**
	 * The user performing the action to audit.
	 */
	private String user;
	/**
	 * The record to audit.
	 */
	private Core<?> data;
	/**
	 * Creation date of the audit.
	 */
	private Date created;
	/**
	 * Key to group pre action audit and post action audit.
	 */
	private String pairKey;
	/**
	 * The request unique key as identifier to trace all audited elements in the same request.
	 */
	private String requestId;

	/**
	 * The table.
	 * @return {@link String} the table
	 */
	@java.lang.SuppressWarnings("all")
	public String getTable() {
		return this.table;
	}

	/**
	 * The auditable table primary key.
	 * @return {@link String} the primaryKey
	 */
	@java.lang.SuppressWarnings("all")
	public String getPrimaryKey() {
		return this.primaryKey;
	}

	/**
	 * The auditable action.
	 * @return {@link AuditableActionsEnum} the action
	 */
	@java.lang.SuppressWarnings("all")
	public AuditableActionsEnum getAction() {
		return this.action;
	}

	/**
	 * The auditable step.
	 * @return {@link AuditableStepsEnum} the step
	 */
	@java.lang.SuppressWarnings("all")
	public AuditableStepsEnum getStep() {
		return this.step;
	}

	/**
	 * The user performing the action to audit.
	 * @return {@link String} the user
	 */
	@java.lang.SuppressWarnings("all")
	public String getUser() {
		return this.user;
	}

	/**
	 * The record to audit.
	 * @return {@link Core} the record
	 */
	@java.lang.SuppressWarnings("all")
	public Core<?> getData() {
		return this.data;
	}

	/**
	 * Creation date of the audit.
	 * @return {@link Date} the created
	 */
	@java.lang.SuppressWarnings("all")
	public Date getCreated() {
		return this.created;
	}

	/**
	 * Key to group pre action audit and post action audit.
	 * @return {@link String} the pairKey
	 */
	@java.lang.SuppressWarnings("all")
	public String getPairKey() {
		return this.pairKey;
	}

	/**
	 * The request unique key as identifier to trace all audited elements in the same request.
	 * @return {@link String} the requestId
	 */
	@java.lang.SuppressWarnings("all")
	public String getRequestId() {
		return this.requestId;
	}

	/**
	 * The table.
	 * @param table {@link String} The table
	 */
	@java.lang.SuppressWarnings("all")
	public void setTable(final String table) {
		this.table = table;
	}

	/**
	 * The auditable table primary key.
	 * @param primaryKey {@link String} The primaryKey
	 */
	@java.lang.SuppressWarnings("all")
	public void setPrimaryKey(final String primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * The auditable action.
	 * @param action {@link AuditableActionsEnum} The action
	 */
	@java.lang.SuppressWarnings("all")
	public void setAction(final AuditableActionsEnum action) {
		this.action = action;
	}

	/**
	 * The auditable step.
	 * @param step {@link AuditableStepsEnum} The step
	 */
	@java.lang.SuppressWarnings("all")
	public void setStep(final AuditableStepsEnum step) {
		this.step = step;
	}

	/**
	 * The user performing the action to audit.
	 * @param user {@link String} The user
	 */
	@java.lang.SuppressWarnings("all")
	public void setUser(final String user) {
		this.user = user;
	}

	/**
	 * The record to audit.
	 * @param data {@link Core} The record
	 */
	@java.lang.SuppressWarnings("all")
	public void setData(final Core<?> data) {
		this.data = data;
	}

	/**
	 * Creation date of the audit.
	 * @param created {@link Date} The created
	 */
	@java.lang.SuppressWarnings("all")
	public void setCreated(final Date created) {
		this.created = created;
	}

	/**
	 * Key to group pre action audit and post action audit.
	 * @param pairKey {@link String} The pairKey
	 */
	@java.lang.SuppressWarnings("all")
	public void setPairKey(final String pairKey) {
		this.pairKey = pairKey;
	}

	/**
	 * The request unique key as identifier to trace all audited elements in the same request.
	 * @param requestId {@link String} The requestId
	 */
	@java.lang.SuppressWarnings("all")
	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof Audit)) return false;
		final Audit other = (Audit) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		if (!super.equals(o)) return false;
		final java.lang.Object this$table = this.getTable();
		final java.lang.Object other$table = other.getTable();
		if (this$table == null ? other$table != null : !this$table.equals(other$table)) return false;
		final java.lang.Object this$primaryKey = this.getPrimaryKey();
		final java.lang.Object other$primaryKey = other.getPrimaryKey();
		if (this$primaryKey == null ? other$primaryKey != null : !this$primaryKey.equals(other$primaryKey)) return false;
		final java.lang.Object this$action = this.getAction();
		final java.lang.Object other$action = other.getAction();
		if (this$action == null ? other$action != null : !this$action.equals(other$action)) return false;
		final java.lang.Object this$step = this.getStep();
		final java.lang.Object other$step = other.getStep();
		if (this$step == null ? other$step != null : !this$step.equals(other$step)) return false;
		final java.lang.Object this$user = this.getUser();
		final java.lang.Object other$user = other.getUser();
		if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
		final java.lang.Object this$data = this.getData();
		final java.lang.Object other$data = other.getData();
		if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
		final java.lang.Object this$created = this.getCreated();
		final java.lang.Object other$created = other.getCreated();
		if (this$created == null ? other$created != null : !this$created.equals(other$created)) return false;
		final java.lang.Object this$pairKey = this.getPairKey();
		final java.lang.Object other$pairKey = other.getPairKey();
		if (this$pairKey == null ? other$pairKey != null : !this$pairKey.equals(other$pairKey)) return false;
		final java.lang.Object this$requestId = this.getRequestId();
		final java.lang.Object other$requestId = other.getRequestId();
		if (this$requestId == null ? other$requestId != null : !this$requestId.equals(other$requestId)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof Audit;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = super.hashCode();
		final java.lang.Object $table = this.getTable();
		result = result * PRIME + ($table == null ? 43 : $table.hashCode());
		final java.lang.Object $primaryKey = this.getPrimaryKey();
		result = result * PRIME + ($primaryKey == null ? 43 : $primaryKey.hashCode());
		final java.lang.Object $action = this.getAction();
		result = result * PRIME + ($action == null ? 43 : $action.hashCode());
		final java.lang.Object $step = this.getStep();
		result = result * PRIME + ($step == null ? 43 : $step.hashCode());
		final java.lang.Object $user = this.getUser();
		result = result * PRIME + ($user == null ? 43 : $user.hashCode());
		final java.lang.Object $data = this.getData();
		result = result * PRIME + ($data == null ? 43 : $data.hashCode());
		final java.lang.Object $created = this.getCreated();
		result = result * PRIME + ($created == null ? 43 : $created.hashCode());
		final java.lang.Object $pairKey = this.getPairKey();
		result = result * PRIME + ($pairKey == null ? 43 : $pairKey.hashCode());
		final java.lang.Object $requestId = this.getRequestId();
		result = result * PRIME + ($requestId == null ? 43 : $requestId.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public Audit() {
	}
}
