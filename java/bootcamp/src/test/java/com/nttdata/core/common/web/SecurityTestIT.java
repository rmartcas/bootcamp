/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import com.nttdata.BaseTest;

/**
 * Test to find insecure mappings in the app.
 */
class SecurityTestIT extends BaseTest {

	@Autowired
	@Qualifier("requestMappingHandlerMapping")
	private RequestMappingHandlerMapping handlerMapping;
	
	@Test
	void findInsecureMappings() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = null;
		List<String> urlNoSeguras = new ArrayList<>();
		
		RequestMethod[] methods = new RequestMethod[] {
				RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.POST,
				RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PATCH,
				RequestMethod.PUT};
		for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
			//Por cada url, testeamos el acceso mediante el método asignado (GET, POST, PUT, DELETE)
			// y comprobamos si nos retorna algo que no sea 401, en cuyo caso fallamos el test
			for (RequestMethod requestMethod : methods) {
                for (PathPattern path : entry.getKey().getPathPatternsCondition().getPatterns()) {

					List<String> lst = new ArrayList<>();
					Pattern pattern = Pattern.compile("\\{\\w+\\}");
                    Matcher matcher = pattern.matcher(path.getPatternString());
				    int count = 0;
				    while (matcher.find()) {
				    	lst.add("" + count++);
				    }
				    
					requestBuilder = MockMvcRequestBuilders.request(
                            HttpMethod.resolve(requestMethod.name()), path.getPatternString(), lst.toArray())
							.param("_csrf", "0");
					ResultActions ra = mockMvc.perform(requestBuilder);
					int status = ra.andReturn().getResponse().getStatus();
					
					boolean redirectIdp = status == 302 && ra.andReturn().getResponse().getHeader("Location").contains("idp");
					if (status != 401 && status != 403 && false == redirectIdp) {
						urlNoSeguras.add("[" + requestMethod.name() +"] " + path);
					}
				}
			}
		}
		
		StringBuilder urlsSinSecurizar = new StringBuilder(
				String.format("Existen %d URLs sin securizar en el fichero seguridad.config.xml " 
						+ "o con la anotaci�n @PreAuthorize en el controller correspondiente:\n", 
						urlNoSeguras.size()));
			for (String url : urlNoSeguras) {
				urlsSinSecurizar.append(url);
				urlsSinSecurizar.append("\n");
			}
			
            assertTrue(urlNoSeguras.isEmpty(), urlsSinSecurizar.toString());
	}
}