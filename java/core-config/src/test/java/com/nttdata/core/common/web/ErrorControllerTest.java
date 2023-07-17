/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.core.common.model.ApiError;

class ErrorControllerTest {
	
	private ErrorController controller = null;
	
	/**
	 * Initialize sut object
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	void initMockWs() throws Exception {
		this.controller = new ErrorController();
	}
	
	@Test
	void testWhenNotFound404isReturned() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		Mockito.doReturn(HttpStatus.NOT_FOUND.value()).when(response).getStatus();
		
		ResponseEntity<ApiError> resp = controller.handleError(request, response);
		Assertions.assertNotNull(resp);
		
		ApiError expectedApiError = new ApiError();
		expectedApiError.setStatus(HttpStatus.NOT_FOUND);
		expectedApiError.setMessage(I18nConstants.I18N_ERROR_PREFIX.concat(String.valueOf(HttpStatus.NOT_FOUND.value())));
		expectedApiError.setErrors(new ArrayList<>());
		Assertions.assertEquals(expectedApiError, resp.getBody());
	}
	
	@Test
	void testWhenBadErrorCode500isReturned() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		Mockito.doReturn(999).when(response).getStatus();
		
		ResponseEntity<ApiError> resp = controller.handleError(request, response);
		Assertions.assertNotNull(resp);
		
		ApiError expectedApiError = new ApiError();
		expectedApiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		expectedApiError.setMessage(I18nConstants.I18N_ERROR_PREFIX.concat(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())));
		expectedApiError.setErrors(new ArrayList<>());
		Assertions.assertEquals(expectedApiError, resp.getBody());
	}
}