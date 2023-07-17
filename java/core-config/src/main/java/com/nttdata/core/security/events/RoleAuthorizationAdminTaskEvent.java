/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import lombok.Getter;
import lombok.ToString;

/**
 * Role authorization admin task event.<br>
 * Event triggered when an authority is assigned or unassigned from a profile.
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@ToString
public class RoleAuthorizationAdminTaskEvent extends AdminTaskEvent {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public enum ActionType {
		ASSIGN,
		UNASSIGN,
		READ
	}
	
	private final String groupId;
	
	private final String roleId;
	
	private final ActionType action;
	
	public RoleAuthorizationAdminTaskEvent(Object source, String groupId, String roleId, ActionType action) {
		super(source, TaskType.ROLE_AUTHORIZATION);
		this.groupId = groupId;
		this.roleId = roleId;
		this.action = action;
	}

	@Override
	public String getDetails() {
		return this.toString();
	}

}
