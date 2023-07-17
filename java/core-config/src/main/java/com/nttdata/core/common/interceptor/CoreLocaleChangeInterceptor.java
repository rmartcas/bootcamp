/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.constants.CommonConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * Locale change interceptor extendending {@link LocaleChangeInterceptor}.<br>
 * Try to change the locale using {@link LocaleChangeInterceptor#getParamName()} if present,
 * otherwise try to change locale using a path variable <code>lang</code>.
 * Valid urls for change are:
 * 	- core-config/api/core/locale/{es|en}
 * 	- core-config/api/core/**?locale={es|en}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class CoreLocaleChangeInterceptor extends LocaleChangeInterceptor {
	
	/** 
	 * Default path variable name to retrieve the new locale.
	 * @param pathVariableName {@link String} The pathVariableName
	 * @return {@link String} the pathVariableName
	 */
	@Getter
	@Setter
	private String pathVariableName = CoreConstants.PATH_VARIABLE_LANG;

	/**
	 * Handles request to change locale using path variable
	 * 
	 * @param request The request to handle
	 * @param response The response
	 * @param handler Handler method
	 * @throws ServletException if error
	 */
	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {

		CoreHttpServletRequestWrapper wrapper = new CoreHttpServletRequestWrapper(request);

		if (null == request.getParameter(getParamName())) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			//Get the annotation @PathVariable from method handler parameters if any:
			for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
				PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
				if (null != pathVariable && pathVariableName.equalsIgnoreCase(pathVariable.name())) {
					//Method has a @PathVariable with name equals pathVariableName, get the value and add to wrapper request parameters
					Map<String, String> lang = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
					wrapper.addParameter(getParamName(), lang.get(getPathVariableName()));
					break;
				}
			}
		}
		return super.preHandle(wrapper, response, handler);
	}
	
	/**
	 * Wrapper class for HttpServletRequest witch allows add new parameters to request
	 * 
	 * @author NTT DATA
	 * @since 0.0.1
	 */
	class CoreHttpServletRequestWrapper extends HttpServletRequestWrapper {
		
		private Map<String, String> params = new HashMap<>();
		
		/**
		 * Instantiates a CoreHttpServletRequestWrapper.
		 *
		 * @param request the request to wrap
		 */
		public CoreHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		/**
		 * Add a new parameter to the request
		 * 
		 * @param paramName the name of the parameter
		 * @param paramValue the value of the parameter
		 */
		public void addParameter(String paramName, String paramValue) {
			params.put(paramName, paramValue);
		}
		
		/**
		 * Gets the requested parameter value.<br>
		 * If parameter exists in wrapped request this is returned
		 *  otherwise return the param in the {@link CoreHttpServletRequestWrapper#params}
		 * 
		 * @param name the parameter to get
		 * @return String parameter value
		 */
		@Override
		public String getParameter(String name) {
			String parameter = super.getParameter(name);
			if (null == parameter) {
				parameter = params.get(name);
			}
			return parameter;
		}
	}
}

