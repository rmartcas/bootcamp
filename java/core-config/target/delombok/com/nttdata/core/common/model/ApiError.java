// Generated by delombok at Tue Jul 18 12:47:58 CEST 2023
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

/**
 * Response object for request that has any type of error
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@JsonInclude(Include.NON_EMPTY)
public class ApiError {
	/**
	 * Response status of api error.
	 */
	private HttpStatus status;
	/**
	 * Response message of api error.
	 */
	private String message;
	/**
	 * List of errors.
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

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ApiError)) return false;
		final ApiError other = (ApiError) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$status = this.getStatus();
		final java.lang.Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		final java.lang.Object this$message = this.getMessage();
		final java.lang.Object other$message = other.getMessage();
		if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
		final java.lang.Object this$errors = this.getErrors();
		final java.lang.Object other$errors = other.getErrors();
		if (this$errors == null ? other$errors != null : !this$errors.equals(other$errors)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ApiError;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		final java.lang.Object $message = this.getMessage();
		result = result * PRIME + ($message == null ? 43 : $message.hashCode());
		final java.lang.Object $errors = this.getErrors();
		result = result * PRIME + ($errors == null ? 43 : $errors.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	public ApiError() {
	}

	/**
	 * Response status of api error.
	 * @return {@link HttpStatus} the status
	 */
	@java.lang.SuppressWarnings("all")
	public HttpStatus getStatus() {
		return this.status;
	}

	/**
	 * Response message of api error.
	 * @return {@link String} the message
	 */
	@java.lang.SuppressWarnings("all")
	public String getMessage() {
		return this.message;
	}

	/**
	 * List of errors.
	 * @return {@link List} the errors
	 */
	@java.lang.SuppressWarnings("all")
	public List<Pair<String, List<Object>>> getErrors() {
		return this.errors;
	}

	/**
	 * Response status of api error.
	 * @param status {@link HttpStatus} The status
	 */
	@java.lang.SuppressWarnings("all")
	public void setStatus(final HttpStatus status) {
		this.status = status;
	}

	/**
	 * Response message of api error.
	 * @param message {@link String} The message
	 */
	@java.lang.SuppressWarnings("all")
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * List of errors.
	 * @param errors {@link List} The errors
	 */
	@java.lang.SuppressWarnings("all")
	public void setErrors(final List<Pair<String, List<Object>>> errors) {
		this.errors = errors;
	}
}
