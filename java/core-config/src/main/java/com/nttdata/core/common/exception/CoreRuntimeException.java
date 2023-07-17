/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.exception;

/**
 * Core runtime exception to extend in applications
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public abstract class CoreRuntimeException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14443223432L;
	
	/** Custom error message to show */
	private final String errorMessage;

	/**
	 * Instantiates a new core runtime exception.
	 *
	 * @param message the message
	 */
	protected CoreRuntimeException(String message) {
		super(message);
		this.errorMessage = message;
	}
	
	/**
	 * Instantiates a new core runtime exception.
	 *
	 * @param t the throwable
	 */
	protected CoreRuntimeException(Throwable t) {
		super(t);
		this.errorMessage = null;
	}
	
	/**
	 * Instantiates a new core runtime exception.
	 *
	 * @param message the message
	 * @param t the throwable
	 */
	protected CoreRuntimeException(String message, Throwable t) {
		super(message, t);
		this.errorMessage = message;
	}
	
	/**
	 * Returns the error message of the exception
	 * 
	 * @return String with error message or null if not defined
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
