/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.interceptor;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hdiv.filter.ValidatorError;
import org.hdiv.util.Constants;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.HtmlUtils;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.exception.BadRequestException;
import com.nttdata.core.i18n.constants.I18nConstants;

/**
 * Interceptor to handle hdiv validation errors for request parameters.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class HdivValidationErrorInterceptor implements HandlerInterceptor {
	
	/**
	 * Handles hdiv parameters validation errors before request reach controllers.
	 * <br>
	 * If any hdiv error {@link BadRequestException is thrown}
	 * 
	 * @param request The request to handle
	 * @param response The response
	 * @param handler Handler method
	 * @throws Exception if error
	 */
	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object validationErrors = request.getAttribute(Constants.EDITABLE_PARAMETER_ERROR);
		if (null != validationErrors) {
			handleHdivErrors((Collection<ValidatorError>) validationErrors);
		}
		return true;
	}
	
	/**
	 * Handles all {@link ValidatorError} on request
	 *
	 * @param validationErrors {@link Collection}&lt;{@link ValidatorError}&gt;
	 * @throws BadRequestException with processed error data
	 */
	protected void handleHdivErrors(Collection<ValidatorError> validationErrors) throws BadRequestException {
		Errors errors = new BeanPropertyBindingResult(null, "request");
		for (ValidatorError validatorError : validationErrors) {
			errors.rejectValue(null, I18nConstants.I18N_VALIDATION_FIELD_INVALID,
					new Object[] {HtmlUtils.htmlEscape(validatorError.getParameterName())}, null);
		}
		throw new BadRequestException(errors);
	}
}

