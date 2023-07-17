/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nttdata.BaseTest;
import com.nttdata.core.audit.constants.AuditableConstants;
import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.audit.model.AuditPage;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.crud.constants.CrudConstants;
import com.nttdata.core.i18n.constants.I18nConstants;

class AuditableControllerTestIT extends BaseTest {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private void addData(MockHttpServletRequestBuilder requestBuilder,
			MockHttpServletRequestBuilder rb, String login) throws Exception {
		
		this.login(rb, login);
		ResultActions ra = mockMvc.perform(rb);

		String token = ra.andReturn().getResponse().getHeader(X_CSRF_TOKEN);
		requestBuilder
			.header(X_CSRF_TOKEN, token)
			.session((MockHttpSession) ra.andReturn().getRequest().getSession());
	}

	private void addInitialData(MockHttpServletRequestBuilder requestBuilder, String login) throws Exception {
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.get(
				AuditableConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		addData(requestBuilder, rb, login);
	}

	@Test
	void testGetAllAuditRecords() throws Exception {
		AuditPage dto = new AuditPage();
		dto.setFilters(new Audit());
		dto.getFilters().setTable("core_USERS");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuditableConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH)
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<AuditPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuditPage>>() {});
		AuditPage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
	}
	
	@Test
	void testGetAllAuditRecordsInvalidData() throws Exception {
		
		AuditPage dto = new AuditPage();
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuditableConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH)
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "auditPage.filters.table\"]}"));
	}
	
	@Test
	void testFindAuditTables() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				AuditableConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testFindByPairKey() throws Exception {
		Audit dto = new Audit();
		dto.setPairKey("1");
		dto.setTable("core_USERS");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuditableConstants.CONTROLLER_NAMESPACE + AuditableConstants.REQUEST_MAPPING_FIND_BY_PAIR_KEY)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<List<Map<?, ?>>> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<List<Map<?, ?>>>>() {});
		List<Map<?, ?>> result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(2, result.size());
	}
	
	@Test
	void testFindByPairKeyWithInvalidData() throws Exception {
		Audit dto = new Audit();
		dto.setTable(new String(new char[251]).replace("\0", "x"));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuditableConstants.CONTROLLER_NAMESPACE + AuditableConstants.REQUEST_MAPPING_FIND_BY_PAIR_KEY)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"" + I18nConstants.I18N_PREFIX + "audit.table\"]}"));
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "audit.pairKey\"]}"));
	}
	
	@Test
	void testSearchByRequestId() throws Exception {
		AuditPage dto = new AuditPage();
		dto.setFilters(new Audit());
		dto.getFilters().setRequestId("123123123");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuditableConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH)
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<AuditPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuditPage>>() {});
		AuditPage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(1, result.getTotalRecords());
		
	}
}