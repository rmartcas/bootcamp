/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.exception;

/**
 * Web exception for rest operation that not found the requested resource
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class NotFoundException extends CoreException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14443223430L;

	/**
	 * Instantiates a not found exception.
	 *
	 * @param message the message
	 */
	public NotFoundException(String message) {
		super(message);
	}
}
