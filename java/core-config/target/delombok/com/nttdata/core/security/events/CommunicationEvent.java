/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.events;

/**
 * Communication event
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class CommunicationEvent extends CoreEvent {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Communication event constructor
	 * @param source {@link Object} should be the url used in the communication
	 */
	public CommunicationEvent(Object source) {
		super(source);
	}

}
