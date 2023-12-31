/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hdiv.util.Constants;
import org.hdiv.util.HDIVUtil;
import org.hdiv.util.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.mappings.service.ApiEndpointService;
import com.nttdata.core.mappings.service.MappingService;
import com.nttdata.core.security.hdiv.CustomLinkUrlProcessor;
import com.nttdata.core.security.recaptcha.annotation.Recaptcha;

import lombok.extern.slf4j.Slf4j;

/**
 * Endpoint service implementation to get the user allowed endpoints
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@Service
public class ApiEndpointServiceImpl implements ApiEndpointService {
	
	/** Handler mapping with all controller mappings */
	@Autowired
	@Qualifier("requestMappingHandlerMapping")
	private RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	private MappingService mappingService;
	
	@Autowired
    @Qualifier("customLinkUrlProcessor")
	private CustomLinkUrlProcessor urlProcessor;
	
	@Autowired
	private ConfigProperties configProperties;

	/* (non-Javadoc)
	 * @see com.nttdata.core.mappings.service.ApiEndpointService#getApiEndpoints()
	 */
	@Override
	public Map<String, Object> getApiEndpoints(CoreUser user) {
		Map<String, Object> endpoints = new HashMap<>();
		
		List<String> userMappings = new ArrayList<>();
	    List<Mapping> apiMappings = new ArrayList<>(); 
		try {
			userMappings = mappingService.getUserMappings(user);
			apiMappings = mappingService.getApiMappings();
		} catch (CoreException e) {
			log.error("Could not get mappings", e);
		}
		
		PathMatcher pathMatcher = handlerMapping.getPathMatcher();
	    Set<Entry<RequestMappingInfo, HandlerMethod>> entrySet = handlerMapping.getHandlerMethods().entrySet();
		for (Entry<RequestMappingInfo, HandlerMethod> entry : entrySet) {
			//Por cada mapping, iteramos por su patrones y métodos y generamos el acceso
			//siempre que el usuario tenga acceso al mapping y no sea una exclusión de hdiv, en cuyo caso
			// no es necesario pasarlo por hdiv.
            PathPatternsRequestCondition patternsCondition = entry.getKey().getPathPatternsCondition();
			if (null != patternsCondition) {
				checkApiEndpointEntry(entry, endpoints, apiMappings, userMappings,
						pathMatcher, patternsCondition.getPatterns());
			}
		}
		
		//add logout url
		endpoints.put("logout", configProperties.getSecurity().getUrl().getLogout());
		return endpoints;
	}

	/**
	 * Verify if the user has access to the requested entry.
	 * <br>
	 * If user is allowed to the path a {@link Map}&lt;{@link String}, {@link Object}&gt; is populated
	 * with tokenized path as map keys like:
	 * 
	 * map["path"]["to"]["endpoint"] = "/path/to/endpoint"
	 *
	 * @param entry {@link Entry}&lt;{@link RequestMappingInfo}, {@link HandlerMethod}&gt; to check
	 * @param endpoints {@link Map}&lt;{@link String}, {@link Object}&gt; where path are stored
	 * @param apiMappings {@link List}&lt;{@link Mapping}&gt; The security list of mappings patterns and access roles
	 * @param userMappings {@link List}&lt;{@link String}&gt; List of allowed patterns user can access
	 * @param pathMatcher {@link PathMatcher} to test path agains request mappings
	 */
	private void checkApiEndpointEntry(Entry<RequestMappingInfo, HandlerMethod> entry, Map<String, Object> endpoints,
			List<Mapping> apiMappings, List<String> userMappings, PathMatcher pathMatcher, Set<PathPattern> patterns) {
		Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
		for (PathPattern path : patterns) {
			if (checkPathAccess(path.getPatternString(), pathMatcher, apiMappings, userMappings)) {
				HttpServletRequest httpRequest = HDIVUtil.getCurrentHttpRequest();
				String contextPath = httpRequest.getContextPath();
				for (RequestMethod method : methods) {
					String processUrl = urlProcessor.processUrl(HDIVUtil.getRequestContext(httpRequest), contextPath + path, Constants.ENCODING_UTF_8, Method.secureValueOf(method.toString()));
					processUrl = processUrl.substring(contextPath.length());
					generatePathMap(endpoints, path.getPatternString(), processUrl, method);
					addRecapchaIfNeccesary(entry, endpoints, path.getPatternString(), method);
				}
			}
		}
	}
	
	private void addRecapchaIfNeccesary(Entry<RequestMappingInfo, HandlerMethod> entry, Map<String, Object> endpoints, String path,
			RequestMethod method) {
		if (configProperties.getSecurity().getRecaptcha().isEnabled()) {
			Recaptcha recaptcha = AnnotationUtils.findAnnotation(entry.getValue().getMethod(), Recaptcha.class);
			if (null == recaptcha) {
				recaptcha = AnnotationUtils.findAnnotation(entry.getValue().getBeanType(), Recaptcha.class);
			}
			if (recaptcha != null) {
				generateRecaptchaPathMap(endpoints, path, method, recaptcha.action());	
			}	
		}
	}
	
	/**
	 * Populate the <code>endpoints</code> map with path and processedPath as value
	 *
	 * @param endpoints {@link Map}&lt;{@link String}, {@link Object}&gt; where path are stored
	 * @param path {@link String} the path to populate
	 * @param processedPath {@link String} hdiv secured path
	 * @param method {@link RequestMethod} the request method
	 */
	@SuppressWarnings(CommonConstants.UNCHECKED)
	protected void generatePathMap(Map<String, Object> endpoints, String path, Object processedPath, RequestMethod method) {
	    int start = 0;
	    String mapPath = path.replace(CoreConstants.API_BASE, CommonConstants.EMPTY_STRING) + CommonConstants.FORWARD_SLASH + method + CommonConstants.FORWARD_SLASH + "url";
	    for (int end; (end = mapPath.indexOf(CommonConstants.FORWARD_SLASH, start)) != -1; start = end + 1) {
	    	if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(mapPath.substring(start, end))) {
	    		endpoints = (Map<String, Object>) endpoints.computeIfAbsent(mapPath.substring(start, end), k -> new HashMap<String, Object>());	    		
	    	}
	    }
	    endpoints.put(mapPath.substring(start), processedPath);
	}
	
	@SuppressWarnings(CommonConstants.UNCHECKED)
	protected void generateRecaptchaPathMap(Map<String, Object> endpoints, String path, RequestMethod method, String action) {
	    int start = 0;
	    String mapPath = path.replace(CoreConstants.API_BASE, CommonConstants.EMPTY_STRING) + CommonConstants.FORWARD_SLASH + method + CommonConstants.FORWARD_SLASH + "action";
	    for (int end; (end = mapPath.indexOf(CommonConstants.FORWARD_SLASH, start)) != -1; start = end + 1) {
	    	if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(mapPath.substring(start, end))) {
	    		endpoints = (Map<String, Object>) endpoints.computeIfAbsent(mapPath.substring(start, end), k -> new HashMap<String, Object>());	    		
	    	}
	    }
	    endpoints.put(mapPath.substring(start), action);
	}

	/**
	 * Check if the user has access to the requested path.
	 * <br>
	 * Path must match al least one {@link Mapping} pattern in the request mapping list and
	 * matched pattern must be in user mapping allowed list.
	 *
	 * @param path {@link String} the path to check
	 * @param pathMatcher {@link PathMatcher} to test path agains request mappings
	 * @param requestMappings {@link List}&lt;{@link Mapping}&gt; The security list of mappings patterns and access roles
	 * @param userMappings {@link List}&lt;{@link String}&gt; List of allowed patterns user can access
	 * @return boolean. True if path has a matching request mapping pattern and user has access to this pattern.
	 */
	protected boolean checkPathAccess(String path, PathMatcher pathMatcher, List<Mapping> requestMappings,
			List<String> userMappings) {
		return null != requestMappings.stream()
			.filter(e -> pathMatcher.match(e.getPattern(), path) 
					&& userMappings.contains(e.getPattern())).findFirst().orElse(null);
	}

}
