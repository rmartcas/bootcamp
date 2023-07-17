/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

/**
 * Sensitive data event
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class SensitiveDataEvent extends CoreEvent {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SensitiveDataEvent(Object source) {
		super(source);
	}

}
