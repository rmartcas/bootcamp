/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.dao.DuplicateKeyException;

import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.core.security.events.InputValidationEvent;
import com.nttdata.core.security.recaptcha.exception.InvalidRecaptchaException;
import com.nttdata.core.common.exception.BadRequestException;
import com.nttdata.core.common.exception.NotFoundException;
import com.nttdata.core.common.exception.WrapperRuntimeException;
import com.nttdata.core.common.model.ApiError;
import com.nttdata.core.context.ApplicationEventPublisherHolder;

/**
 * Handles all type of exception and return appropriate response type
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Handles internal server errors.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler()
	protected ResponseEntity<Object> handleInternalServerError(RuntimeException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		
		String message = I18nConstants.I18N_ERROR_PREFIX.concat(String.valueOf(httpStatus.value()));
		ApiError apiError = new ApiError(httpStatus, message);
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
	}
	
	/**
	 * Handles wrapped exceptions.
	 * 
	 * If the wrapped exception has a custom exception handlers delegate to this handler. Otherwise generic handler is used.
	 * 
	 * @param ex {@link WrapperRuntimeException} the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({WrapperRuntimeException.class})
	protected ResponseEntity<Object> handleWrapperRuntimeException(WrapperRuntimeException ex, WebRequest request) {
		Throwable cause = ex.getCause();
		if (null != cause && !ex.equals(cause)) {
			return this.handleCause(cause, request);
		}
		return this.handleInternalServerError(ex, request);
	}
	
	/**
	 * Handle max upload size exceeded exceptions.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({MaxUploadSizeExceededException.class})
	protected ResponseEntity<Object> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		String message = I18nConstants.I18N_ERROR_PREFIX.concat(String.valueOf(httpStatus.value()));
		
		ApiError apiError = new ApiError(httpStatus, message);
		List<Pair<String, List<Object>>> errores = new ArrayList<>();
		apiError.setErrors(errores);
		
		Pair<String, List<Object>> errPair = Pair.of(I18nConstants.I18N_VALIDATION_FILE_MAX_SIZE_UPLOAD_EXCEEDED, 
				Arrays.asList(ex.getMaxUploadSize() / 1024 / 1024));
		errores.add(errPair);
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
	}
	
	/**
	 * Handle duplicated key exceptions.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({DuplicateKeyException.class})
	protected ResponseEntity<Object> handleDuplicateKey(DuplicateKeyException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.CONFLICT;
		
		String message = I18nConstants.I18N_VALIDATION_FIELD_DUPLICATED;
		ApiError apiError = new ApiError(httpStatus, message);
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
	}
	
    /** Handle data integrity violation exceptions.
     *  @param ex the ex
     *  @param request the request
     *  @return the response entity
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        String message = I18nConstants.I18N_VALIDATION_RECORD_IN_USE;
        ApiError apiError = new ApiError(httpStatus, message);
        
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
    }
	
	/**
	 * Handle access denied exceptions.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({ AccessDeniedException.class, InvalidRecaptchaException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		
		String message = I18nConstants.I18N_ERROR_PREFIX.concat(String.valueOf(httpStatus.value()));
		ApiError apiError = new ApiError(httpStatus, message);
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
    }
	
	/**
	 * Handle not found exceptions.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ApiError apiError = new ApiError(httpStatus, ex.getErrorMessage());
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
    }
	
	/**
	 * Handle bad request exceptions.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ApiError apiError = new ApiError(httpStatus, ex.getErrorMessage());
		
		if (null != ex.getErrors() && ex.getErrors().hasErrors()) {
			List<Pair<String, List<Object>>> errors = new ArrayList<>();
			for (ObjectError error : ex.getErrors().getAllErrors()) {
				List<Object> errArgs = null;
				if (null != error.getArguments()) {
					errArgs = Arrays.asList(error.getArguments());
				}
				Pair<String, List<Object>> errPair = Pair.of(error.getCode(), errArgs);
				errors.add(errPair);
			}
			apiError.setErrors(errors);
		}	
        ApplicationEventPublisherHolder.getEventPublisher().publishEvent(new InputValidationEvent(ex));
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
    }
	
	protected ResponseEntity<Object> handleCause(Throwable cause, WebRequest request) {
		if (cause instanceof BadRequestException) {
			return this.handleBadRequestException((BadRequestException) cause, request);
		}
		return this.handleInternalServerError((RuntimeException) cause, request);
	}
}
