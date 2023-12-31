/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.hdiv;

import org.hdiv.context.RequestContextHolder;
import org.hdiv.dataComposer.IDataComposer;
import org.hdiv.urlProcessor.LinkUrlProcessor;
import org.hdiv.urlProcessor.UrlData;
import org.hdiv.util.Method;

import com.nttdata.core.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom url processor that allow generate a state for an action and method.
 * 
 * @see ValidatorHelperRequestWrapper
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class CustomLinkUrlProcessor extends LinkUrlProcessor {
	
	public CustomLinkUrlProcessor() {
		super();
	}
	
	public CustomLinkUrlProcessor(LinkUrlProcessor p) {
		super();
		this.setConfig(SecurityUtils.getFieldValue(p, "config"));
	}
	
	/**
	 * Process the url to add hdiv state if it is necessary.
	 * 
	 * @param request {@link RequestContextHolder} object
	 * @param url url to process
	 * @param encoding char encoding
	 * @return processed url
	 */
	@Override
	public String processUrl(final RequestContextHolder request, String url, final String encoding) {
		return this.processUrl(request, url, encoding, Method.GET);
	}
	
	/**
	 * Process the url to add hdiv state if it is necessary.
	 * 
	 * @param request {@link RequestContextHolder} object
	 * @param url url to process
	 * @param encoding char encoding
	 * @param method the method to protect
	 * @return processed url
	 */
	public String processUrl(final RequestContextHolder request, String url, final String encoding, Method method) {

		if (request == null) {
			return url;
		}
		IDataComposer dataComposer = request.getDataComposer();
		if (dataComposer == null) {
			// IDataComposer not initialized on request, request is out of filter
			if (log.isDebugEnabled()) {
				log.debug("IDataComposer not initialized on request. Request doesn't pass through ValidatorFilter, review it's mapping");
			}
			return url;
		}
		String hdivParameter = dataComposer.getHdivParameterName();
		UrlData urlData = createUrlData(url, method, hdivParameter, request);
		if (urlData.isHdivStateNecessary(config)) {
			// the url needs protection
			dataComposer.beginRequest(method, urlData.getUrlWithoutContextPath());

			urlData.setComposedUrlParams(dataComposer.composeParams(urlData.getUrlParams(), method, encoding));

			// Hdiv state param value
			String stateParam = dataComposer.endRequest();
			// Url with confidential values and hdiv state param

			url = getProcessedUrlWithHdivState(dataComposer.getBuilder(), hdivParameter, urlData, stateParam);
		}

		return url;
	}
}