/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object for requests in the application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode
@Getter
@JsonInclude(Include.NON_EMPTY)
public class ApiResponse<T> {
 
	/** 
	 * Response message.
	 * @return the response
	 */
    private final T response;
    
    @Setter
    private boolean refresh;
    
    @ConstructorProperties({"response"})
    public ApiResponse(T resp) {
    	this.response = resp;
	}
 
}