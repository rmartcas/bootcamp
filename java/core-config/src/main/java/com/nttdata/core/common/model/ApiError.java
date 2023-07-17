/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response object for request that has any type of error
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ApiError {
 
	/** 
	 * Response status of api error.
	 * @param status {@link HttpStatus} The status
	 * @return {@link HttpStatus} the status
	 */
    private HttpStatus status;
    /** 
	 * Response message of api error.
	 * @param message {@link String} The message
	 * @return {@link String} the message
	 */
    private String message;
    /** 
	 * List of errors.
	 * @param errors {@link List} The errors
	 * @return {@link List} the errors
	 */
    private List<Pair<String, List<Object>>> errors;
 
    /** 
     * Create a new ApiError with specified status, message and errors
     *  
     * @param status HttpStatus to response
     * @param message The message to show
     * @param errors The errors to show
     */
    public ApiError(HttpStatus status, String message, String... errors) {
        super();
        this.status = status;
        this.message = message;
        
        this.errors = new ArrayList<>();
        if (errors != null) {
        	Pair<String, List<Object>> pairError;
        	for (String error : errors) {
				pairError = Pair.of(error, new ArrayList<Object>());
				this.errors.add(pairError);
			}
        }
    }
}