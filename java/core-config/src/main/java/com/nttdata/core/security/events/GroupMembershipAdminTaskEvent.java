/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import lombok.Getter;
import lombok.ToString;

/**
 * Group membership admin task event.<br>
 * Event triggered when a profile is assigned or unassigned from a user.
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@ToString
public class GroupMembershipAdminTaskEvent extends AdminTaskEvent {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public enum ActionType {
		ASSIGN,
		UNASSIGN
	}
	
	private final String userId;
	
	private final String groupId;
	
	private final ActionType action;

	public GroupMembershipAdminTaskEvent(Object source, String userId, String groupId, ActionType action) {
		super(source, TaskType.GROUP_MEMBERSHIP);
		this.userId = userId;
		this.groupId = groupId;
		this.action = action;
	}

	@Override
	public String getDetails() {
		return this.toString();
	}

}
