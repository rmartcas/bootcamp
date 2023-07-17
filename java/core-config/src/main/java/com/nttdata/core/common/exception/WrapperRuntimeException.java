/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.exception;

/**
 * Wrapper runtime exception to wrap other exceptions
 * 
 * @author NTT DATA
 * @since 0.0.1
 *
 */
public class WrapperRuntimeException extends CoreRuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a wrapper exception with specified Throwable
	 *
	 * @param t Throwable
	 */
	public WrapperRuntimeException(Throwable t) {
		super(t);
	}

}
