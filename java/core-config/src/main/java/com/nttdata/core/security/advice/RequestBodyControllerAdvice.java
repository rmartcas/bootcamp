/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.hdiv.config.HDIVConfig;
import org.hdiv.exception.HDIVException;
import org.hdiv.filter.ValidatorError;
import org.hdiv.logs.Logger;
import org.hdiv.util.Constants;
import org.hdiv.util.HDIVErrorCodes;
import org.hdiv.util.HDIVUtil;
import org.hdiv.validator.EditableDataValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewRequestBodyAdvice;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import com.nttdata.core.common.exception.BadRequestException;
import com.nttdata.core.common.exception.WrapperRuntimeException;
import com.nttdata.core.common.utils.ValidatorUtils;
import com.nttdata.core.i18n.constants.I18nConstants;

import lombok.extern.slf4j.Slf4j;
														  
/**
 * Validate all parameters inside body of a request agains hdiv rules
 * 
 * @author NTT DATA
 * @since 0.0.1
 *
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Order(Ordered.HIGHEST_PRECEDENCE - 1)
@Slf4j
public class RequestBodyControllerAdvice extends JsonViewRequestBodyAdvice {
	
	/** Logger to print the possible attacks detected by HDIV */
	@Autowired
	private Logger hdivSecurityLog;
	
	/** HDIV configuration object. */
	@Autowired
	private HDIVConfig hdivConfig;	
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * JsonViewRequestBodyAdvice
	 * #supports(org.springframework.core.MethodParameter,
	 * java.lang.reflect.Type, java.lang.Class)
	 */
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return null != methodParameter.getParameterAnnotation(RequestBody.class)
				|| null != methodParameter.getParameterAnnotation(RequestPart.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * JsonViewRequestBodyAdvice
	 * #beforeBodyRead(org.springframework.http.HttpInputMessage,
	 * org.springframework.core.MethodParameter, java.lang.reflect.Type,
	 * java.lang.Class)
	 */
	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
			MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType)
			throws IOException {
		
		byte[] byteArray = validateRequest(inputMessage, methodParameter.getParameterType().getSimpleName());
		
		InputStream body = new ByteArrayInputStream(byteArray);
		JsonView annotation = methodParameter.getParameterAnnotation(JsonView.class);
		if (null != annotation) {
			Class<?>[] classes = annotation.value();
			if (classes.length != 1) {
				throw new IllegalArgumentException(
						"@JsonView only supported for request body advice with exactly 1 class argument: " 
				+ methodParameter);
			}
			return new MappingJacksonInputMessage(body, inputMessage.getHeaders(), classes[0]);
		} else {
			return new MappingJacksonInputMessage(body, inputMessage.getHeaders());
		}
	}

	/**
	 * Validate request against Hdiv validation rules
	 * @param inputMessage {@link HttpInputMessage} with body data
	 * @param modelName {@link String} the model name
	 * @return byte array of request body data.
	 * @throws Exception if error
	 */
	private byte[] validateRequest(HttpInputMessage inputMessage, String modelName)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		MapType type = objectMapper.getTypeFactory().constructMapType(
				Map.class, String.class, Object.class);

		byte[] byteArray = getBodyByteArray(inputMessage);
		
		Map<String, Object> data = objectMapper.readValue(byteArray, type);

		Map<String, String[]> unauthorizedParameters = new HashMap<>();
		
		validateRequestBody(StringUtils.EMPTY, data, unauthorizedParameters);

		if (!unauthorizedParameters.isEmpty()) {
			try {
				createErrorMessages(unauthorizedParameters, modelName);
			} catch (BadRequestException e) {
				throw new WrapperRuntimeException(e);
			}
		}
		return byteArray;
	}

	/**
	 * Get the request body from {@link HttpInputMessage}
	 * @param inputMessage {@link HttpInputMessage}
	 * @return byte array of request body data.
	 * @throws IOException if error
	 */
	private byte[] getBodyByteArray(HttpInputMessage inputMessage)
			throws IOException {
		return StreamUtils.copyToByteArray(inputMessage.getBody());
	}

 
	/**
	 * Validate the request body.
	 * @param field {@link String} the field to validate.
	 * @param data {@link Map}&lt;{@link String}, {@link Object}&gt; the request body
	 * @param unauthorizedParameters {@link Map}&lt;{@link String}, {@link String}[]&gt; invalid parameters
	 */
	private void validateRequestBody(String field, Map<String, Object> data, 
			Map<String, String[]> unauthorizedParameters) {
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String currentField = StringUtils.removeStart(
					StringUtils.join(new String[]{field, entry.getKey()}, "."), ".");
			Object value = entry.getValue();
			validateField(unauthorizedParameters, currentField, value);
		}
	}

 
	/**
	 * Validate a single field
	 * @param unauthorizedParameters {@link Map}&lt;{@link String}, {@link String}[]&gt; invalid parameters
	 * @param currentField {@link} the field to validate
	 * @param value {@link Object} the value of the field
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void validateField(Map<String, String[]> unauthorizedParameters,
			String currentField, Object value) {
		if (value instanceof Map) {
			validateRequestBody(currentField, (Map) value, unauthorizedParameters);
		} else if(value instanceof List) {
			List valueList = (List) value;
			for (Object object : valueList) {
				validateField(unauthorizedParameters, currentField, object);
			}
		} else if (null != value) {
			validateEditableParameter(currentField, new String[]{ value.toString() }, "text", unauthorizedParameters);
		}
	}
	
 
	/**
	 * Validate a single parameter.
	 * 
	 * @param parameter
	 *            parameter name
	 * @param values
	 *            parameter's values
	 * @param dataType
	 *            editable data type
	 * @param unauthorizedParameters
	 *            Unauthorized editable parameters
	 * @since HDIV 1.1
	 */
	private void validateEditableParameter(String parameter,
			String[] values, String dataType, Map<String, String[]> unauthorizedParameters) {

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String target = this.getTarget(sra);
		
		if (hdivConfig.isParameterWithoutValidation(target, parameter)) {
			if (log.isDebugEnabled()) {
				log.debug("parameter " + parameter + " doesn't need validation. It is user defined parameter.");
			}
			return;
		}
		
		EditableDataValidationResult result = hdivConfig.getEditableDataValidationProvider()
				.validate(target, parameter, values, dataType);
		if (!result.isValid()) {
			StringBuilder unauthorizedValues = new StringBuilder(values[0]);

			for (int i = 1; i < values.length; i++) {
				unauthorizedValues.append("," + values[i]);
			}

			if ("password".equals(dataType)) {
				String[] passwordError = { Constants.HDIV_EDITABLE_PASSWORD_ERROR_KEY };
				unauthorizedParameters.put(parameter, passwordError);
			} else {
				unauthorizedParameters.put(parameter, values);
			}
			
			ValidatorError error = new ValidatorError(HDIVErrorCodes.INVALID_EDITABLE_VALUE, target, parameter,
					unauthorizedValues.toString(), null, result.getValidationId());

			hdivSecurityLog.log(error);																	
		}
	}
	
	/**
	 * Gets the part of the url that represents the action to be executed in this request.
	 * 
	 * @param sra
	 *            HttpServletRequest to validate
	 * @return target {@link String} Part of the url that represents the target action
	 */
	protected String getTarget(ServletRequestAttributes sra) {
		HttpServletRequest request = null;
		try {
			request = sra.getRequest();
			String requestUri = request.getRequestURI();
			requestUri = HDIVUtil.stripSession(requestUri).substring(request.getContextPath().length());
			return URLDecoder.decode(requestUri, Constants.ENCODING_UTF_8);
		} catch (Exception e) {
			String errorMessage = HDIVUtil.getMessage(request, "helper.actionName");
			throw new HDIVException(errorMessage, e);
		}
	}
	
	/**
	 * Create the error messages and throw the {@link BadRequestException}.
	 * @param unauthorizedParameters {@link Map}<{@link String}, {@link String}[]> invalid parameters
	 * @param modelName {@link String} the model name
	 * @throws BadRequestException if any invalid parameter
	 */
	private void createErrorMessages(final Map<String, String[]> unauthorizedParameters, 
			String modelName) throws BadRequestException {
		
		List<String> fields = this.getInvalidHdivFields(unauthorizedParameters);
		
		Errors errors = null;
		if (modelName != null) {
			String objectName = Character.toLowerCase(modelName.charAt(0)) + modelName.substring(1);
			errors = new BeanPropertyBindingResult(null, objectName);
			for (String field : fields) {
				errors.rejectValue(null, I18nConstants.I18N_VALIDATION_FIELD_INVALID,
						new Object[] {HtmlUtils.htmlEscape(ValidatorUtils.getFullFieldName(errors, field))}, null);
			}
		}
		
		throw new BadRequestException(errors);
	}	
	
	/**
	 * For the current request get all invalid parameters for HDIV
	 * @param unauthorizedParameters The parameters that raise the error.
	 * @return List de String con los nobmres de campos
	 */
	private List<String> getInvalidHdivFields(final Map<String, String[]> unauthorizedParameters) {
		
		if ((unauthorizedParameters == null) || unauthorizedParameters.isEmpty()) {
			return Collections.emptyList();
		}
		
		return new ArrayList<>(unauthorizedParameters.keySet());
	}
}