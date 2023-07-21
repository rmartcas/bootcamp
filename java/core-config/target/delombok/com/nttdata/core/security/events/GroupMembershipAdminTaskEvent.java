// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

/**
 * Group membership admin task event.<br>
 * Event triggered when a profile is assigned or unassigned from a user.
 * @author NTT DATA
 * @since 0.0.1
 */
public class GroupMembershipAdminTaskEvent extends AdminTaskEvent {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	public enum ActionType {
		ASSIGN, UNASSIGN;
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

	@java.lang.SuppressWarnings("all")
	public String getUserId() {
		return this.userId;
	}

	@java.lang.SuppressWarnings("all")
	public String getGroupId() {
		return this.groupId;
	}

	@java.lang.SuppressWarnings("all")
	public ActionType getAction() {
		return this.action;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public java.lang.String toString() {
		return "GroupMembershipAdminTaskEvent(userId=" + this.getUserId() + ", groupId=" + this.getGroupId() + ", action=" + this.getAction() + ")";
	}
}