/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import lombok.Getter;
import lombok.ToString;

/**
 * Object authorization admin task event
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@ToString
public class ObjectAuthorizationAdminTaskEvent extends AdminTaskEvent {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public enum ActionType {
		ASSIGN,
		UNASSIGN
	}
	
	private final String groupId;
	
	private final String objectId;
	
	private final String roleId;
	
	private final ActionType action;
	
	public ObjectAuthorizationAdminTaskEvent(Object source, String groupId,
			String objectId, String roleId, ActionType action) {
		super(source, TaskType.OBJECT_AUTHORIZATION);
		this.groupId = groupId;
		this.objectId = objectId;
		this.roleId = roleId;
		this.action = action;
	}

	@Override
	public String getDetails() {
		return this.toString();
	}

}
