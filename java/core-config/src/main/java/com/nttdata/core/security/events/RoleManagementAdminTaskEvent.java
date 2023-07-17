/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import lombok.Getter;
import lombok.ToString;

/**
 * Role management admin task event.<br>
 * Event triggered when an authority entity is created/updated/deleted.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@ToString
public class RoleManagementAdminTaskEvent extends AdminTaskEvent {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public enum ActionType {
		CREATE,
		MODIFY,
		DELETE,
		READ
	}
	
	private final String roleId;
	
	private final ActionType action;
	
	private final boolean privileged;

	public RoleManagementAdminTaskEvent(Object source, String roleId, ActionType action, boolean privileged) {
		super(source, TaskType.ROLE_MANAGEMENT);
		this.roleId = roleId;
		this.action = action;
		this.privileged = privileged;
	}

	@Override
	public String getDetails() {
		return this.toString();
	}

}
