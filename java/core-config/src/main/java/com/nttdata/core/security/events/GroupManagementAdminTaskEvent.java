/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import lombok.Getter;
import lombok.ToString;

/**
 * Group management admin task event.<br>
 * Event triggered when a profile is created or destroyed.
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@ToString
public class GroupManagementAdminTaskEvent extends AdminTaskEvent {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public enum ActionType {
		CREATE,
		DESTROY
	}
	
	private final String groupId;
	
	private final ActionType action;

	public GroupManagementAdminTaskEvent(Object source, String groupId, ActionType action) {
		super(source, TaskType.GROUP_MANAGEMENT);
		this.groupId = groupId;
		this.action = action;
	}

	@Override
	public String getDetails() {
		return this.toString();
	}

}
