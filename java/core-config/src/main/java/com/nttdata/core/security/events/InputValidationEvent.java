/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

/**
 * Input validation event
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class InputValidationEvent extends CoreEvent {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public InputValidationEvent(Object source) {
		super(source);
	}

}
