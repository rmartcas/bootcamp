/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.hdiv;

import org.hdiv.context.RequestContextHolder;
import org.hdiv.filter.ValidationContext;
import org.hdiv.filter.ValidatorError;
import org.hdiv.filter.ValidatorHelperRequest;
import org.hdiv.filter.ValidatorHelperResult;
import org.hdiv.state.IState;
import org.hdiv.state.State;
import org.hdiv.util.HDIVErrorCodes;
import org.hdiv.util.Method;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriTemplate;

import com.nttdata.core.security.utils.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom Validator Helper Request that allows extra security validations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class ValidatorHelperRequestWrapper extends ValidatorHelperRequest {
		
	public ValidatorHelperRequestWrapper() {
		super();
	}
	
	/**
	 * Initialize the new {@link ValidatorHelperRequestWrapper} with the same data as
	 * the received {@link ValidatorHelperRequest}
	 * @param r {@link ValidatorHelperRequest}
	 */
	public ValidatorHelperRequestWrapper(ValidatorHelperRequest r) {
		this.setStateUtil(SecurityUtils.getFieldValue(r, "stateUtil"));
		this.setHdivConfig(SecurityUtils.getFieldValue(r, "hdivConfig"));
		this.setSession(SecurityUtils.getFieldValue(r, "session"));
		this.setDataValidator(SecurityUtils.getFieldValue(r, "dataValidator"));
		this.setUrlProcessor(SecurityUtils.getFieldValue(r, "urlProcessor"));
		this.setDataComposerFactory(SecurityUtils.getFieldValue(r, "dataComposerFactory"));
		this.setStateScopeManager(SecurityUtils.getFieldValue(r, "stateScopeManager"));
		this.init();
	}
	
	@Override
	public ValidatorHelperResult validate(ValidationContext context) {
		ValidatorHelperResult result = super.validate(context);
		if (!result.isValid()) {
			return result;
		}
		ValidatorHelperResult state = this.restoreState(context);
		return this.isTheSameMethod(context.getMethod(), state.getValue());
	}
	
	/**
	 * Checks if the method received in the request is the same as the one stored in the HDIV state.
	 *
	 * @param method {@link Method} the request method
	 * @param state {@link IState} The restored state for this url
	 * @return valid result if the methods are the same. False otherwise.
	 */
	protected ValidatorHelperResult isTheSameMethod(final Method method, IState state) {
		
		if (null == state) {
			//No state, maybe is a validation not required target
			return ValidatorHelperResult.VALIDATION_NOT_REQUIRED;
		}

		if (state.contains(method)) {
			return ValidatorHelperResult.VALID;
		}

		if (log.isDebugEnabled()) {
			log.debug("Validation error in the method. Method in state [" + ((State)state).getMethod() + "], method in the request [" + method + "]");
		}
		ValidatorError error = new ValidatorError("INVALID_METHOD", state.getAction());
		return new ValidatorHelperResult(error);
	}
	
	@Override
	protected ValidatorHelperResult isTheSameAction(RequestContextHolder context, String target, String stateAction) {
		stateAction = HtmlUtils.htmlUnescape(stateAction);

		if (stateAction.equalsIgnoreCase(target)) {
			return ValidatorHelperResult.VALID;
		}

		if (target.endsWith("/")) {
			String actionSlash = stateAction + "/";
			if (actionSlash.equalsIgnoreCase(target)) {
				return ValidatorHelperResult.VALID;
			}
		}
		
		UriTemplate uriTemplate = new UriTemplate(stateAction);
		if (uriTemplate.matches(target)) {
			return ValidatorHelperResult.VALID;
		}

		if (log.isDebugEnabled()) {
			log.debug("Validation error in the action. Action in state [" + stateAction + "], action in the request [" + target + "]");
		}
		ValidatorError error = new ValidatorError(HDIVErrorCodes.INVALID_ACTION, target);
		return new ValidatorHelperResult(error);
	}
}
