/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.nttdata.BaseTest;
import com.nttdata.core.common.constants.CoreConstants;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.common.model.CoreConfig;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.bootcamp.common.model.ApplicationUser;

class CoreControllerTestIT extends BaseTest {
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	@Test
	void testGetCoreConfigWithValidSession() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				CoreConstants.CORE_CONTROLLER_NAMESPACE + CoreConstants.REQUEST_MAPPING_CONFIG );

		this.login(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		if (null != ra.andReturn().getResolvedException()) {
			throw ra.andReturn().getResolvedException();
		}
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
        SimpleModule module = new SimpleModule();
        StdDeserializer<CoreUser> deserializer = new StdDeserializer<CoreUser>(CoreUser.class) {

            /** serialVersionUID */
            private static final long serialVersionUID = 1L;

            @Override
            public ApplicationUser deserialize(JsonParser p, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {
                return p.readValueAs(ApplicationUser.class);
            }
        };
        
        module.addDeserializer(CoreUser.class, deserializer);
        mapper.registerModule(module);
		
		ApiResponse<CoreConfig> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<CoreConfig>>() {});
		CoreConfig resp = apiResponse.getResponse();
		
		Assertions.assertNotNull(resp);
		
		CoreConfig expectedConfig = new CoreConfig();
		expectedConfig.setLocale("en");
		expectedConfig.setDefaultLocale("es");
		expectedConfig.setLocales(Arrays.asList(configProperties.getLocale().getLocales()));
		expectedConfig.setPagination(resp.getPagination());
		expectedConfig.setMenus(resp.getMenus());
		expectedConfig.setRecaptcha(resp.getRecaptcha());
		
		CoreUser expectedUser = new ApplicationUser();
		expectedUser.setId(-1L);
		expectedUser.setName("Manager");
		expectedUser.setProfileId(1L);
		expectedUser.setUsername("E000000");
		expectedUser.setAuthorities(resp.getUser().getAuthorities());
		
		expectedConfig.setUser(expectedUser);
		
		Map<String, Object> apis = resp.getApi();
		expectedConfig.setApi(apis);
		
		Assertions.assertNotNull(apis.get("core"));
		Assertions.assertEquals(expectedConfig, resp);
	}
	
	@Test
	void testGetCoreLocaleWithValidSession() throws Exception {		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				CoreConstants.CORE_CONTROLLER_NAMESPACE + CoreConstants.REQUEST_MAPPING_LOCALE , "en");
		this.login(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		ApiResponse<Map<String, String>> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Map<String, String>>>() {});
		Map<String, String> resp = apiResponse.getResponse();
		
		Assertions.assertNotNull(resp);
		Assertions.assertTrue(resp.containsKey("i18n.error.403"));
		Assertions.assertEquals("You have no permissions to access the requested functionality", resp.get("i18n.error.403"));
	}
	
	@Test
	void testGetCoreLocaleWithInvalidLocale() throws Exception {		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				CoreConstants.CORE_CONTROLLER_NAMESPACE + CoreConstants.REQUEST_MAPPING_LOCALE , "klingon");
		this.login(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		Assertions.assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"lang\"]}"));
	}
	
	@Test
	void testGetCoreConfigWithoutAnyRole() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				CoreConstants.CORE_CONTROLLER_NAMESPACE + CoreConstants.REQUEST_MAPPING_CONFIG );
		
        this.login(requestBuilder, "E000010");
        ResultActions ra = mockMvc.perform(requestBuilder);
        ra.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
}
