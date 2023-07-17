/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.model.ApiError;
import com.nttdata.core.i18n.constants.I18nConstants;


import lombok.extern.slf4j.Slf4j;

/**
 * Handles internal error and return rest response with error info
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping(CoreConstants.ERROR_CONTROLLER_NAMESPACE)
class ErrorController {
	
	/**
	 * Handles errors
	 * 
	 * @param response the response
	 * @param errorCode int with error code to handle
	 * @return ResponseEntity with error details
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiError> handleError(HttpServletRequest request, HttpServletResponse response) {		
		HttpStatus httpStatus = getHttpStatus(response.getStatus());
		
		String message = I18nConstants.I18N_ERROR_PREFIX.concat(String.valueOf(httpStatus.value()));
		
		ApiError apiError = new ApiError(httpStatus, message);
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	/**
	 * Get the http status of the error
	 * @param errorCode the http error code
	 * @return HttpStatus
	 */
	private HttpStatus getHttpStatus(int errorCode) {
		HttpStatus httpStatus = null;
		try {
			httpStatus = HttpStatus.valueOf(errorCode);
		} catch (IllegalArgumentException e) {
			log.debug("Invalid error code.", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return httpStatus;
	}
}