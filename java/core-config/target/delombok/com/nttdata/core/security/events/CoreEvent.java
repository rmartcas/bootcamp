/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

import org.springframework.context.ApplicationEvent;

/**
 * Abstract core event. All security events should inherit from this.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public abstract class CoreEvent extends ApplicationEvent {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected CoreEvent(Object source) {
		super(source);
	}

}
