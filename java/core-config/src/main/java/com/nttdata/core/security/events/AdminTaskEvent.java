/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import lombok.Getter;

/**
 * Admin task event.<br>
 * Base event for all administrative task performed by the application.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
public abstract class AdminTaskEvent extends CoreEvent {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Allowed administration task types
	 */
	enum TaskType {
		GROUP_MANAGEMENT,
		GROUP_MEMBERSHIP,
		ROLE_MANAGEMENT,
		ROLE_AUTHORIZATION,
		OBJECT_AUTHORIZATION
	}
	
	private final TaskType taskType;

	protected AdminTaskEvent(Object source, TaskType taskType) {
		super(source);
		this.taskType = taskType;
	}

	/**
	 * Get the event task details
	 *
	 * @return {@link String} the task details
	 */
	public abstract String getDetails();
}
