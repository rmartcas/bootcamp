/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.BaseTest;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.common.model.PageOrder;
import com.nttdata.core.crud.constants.CrudConstants;
import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.core.mappings.constants.MappingConstants;
import com.nttdata.core.mappings.model.MappingDataLoad;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.mappings.model.MappingPage;
import com.nttdata.core.authorities.model.Authority;

/**
 * Test mapping module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class MappingControllerTestIT extends BaseTest {

	private ObjectMapper mapper = new ObjectMapper();
	
	private void addData(MockHttpServletRequestBuilder requestBuilder,
			MockHttpServletRequestBuilder rb) throws Exception {
		
		this.login(rb, "E000000");
		ResultActions ra = mockMvc.perform(rb);

		String token = ra.andReturn().getResponse().getHeader(X_CSRF_TOKEN);
		requestBuilder
			.header(X_CSRF_TOKEN, token)
			.session((MockHttpSession) ra.andReturn().getRequest().getSession());
	}
	
	private void addInitialEditData(MockHttpServletRequestBuilder requestBuilder, String login) throws Exception {
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.get(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		addData(requestBuilder, rb);
	}

	private void addInitialData(MockHttpServletRequestBuilder requestBuilder, String login) throws Exception {
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.get(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		addData(requestBuilder, rb);
	}
	
	@Test
	void testSearch() throws Exception {
		MappingPage dto = new MappingPage();
		dto.setFilters(new Mapping());
		dto.getFilters().setPattern("/api/core/");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MappingPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MappingPage>>() {});
		MappingPage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(100, result.getRecords().size());
	}
	
	@Test
	void testSearchWithEmptyResults() throws Exception {
		MappingPage dto = new MappingPage();
		dto.setFilters(new Mapping());
		dto.getFilters().setPattern("notfoundname");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MappingPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MappingPage>>() {});
		MappingPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(100, result.getRecords().size());
	}
	
	@Test
	void testSearchWithOrder() throws Exception {
		MappingPage dto = new MappingPage();
		
		List<PageOrder> orders = new ArrayList<>();
		orders.add(new PageOrder("filters.pattern", Direction.ASC));
		orders.add(new PageOrder("filters.position", Direction.ASC));
		orders.add(new PageOrder("notExist", Direction.ASC));
		
		dto.setPageOrder(orders);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MappingPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MappingPage>>() {});
		MappingPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
	}
	
	@Test
	void testSearchWithInvalidData() throws Exception {
		MappingPage dto = new MappingPage();
		dto.setFilters(new Mapping());
		dto.getFilters().setPattern("< invalid char");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"" + I18nConstants.I18N_PREFIX + "mappingPage.filters.pattern\"]}"));
	}
	
	@Test
	void testFind() throws Exception {
		Mapping dto = new Mapping();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Mapping> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Mapping>>() {});
		Mapping result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(2L), result.getId());
		assertEquals("/api/core/**", result.getPattern());
		assertNotNull(result.getAuthorities());
		assertFalse(result.getAuthorities().isEmpty());
	}
	
	@Test
	void testInsert() throws Exception {
		Mapping dto = new Mapping();
        dto.setPattern("/api/mappings/validate");
        dto.setPosition((byte) 99);
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Mapping> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Mapping>>() {});
		Mapping result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	@Test
	void testInsertWithInvalidData() throws Exception {
		Mapping dto = new Mapping();
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "mapping.pattern\"]}"));
	}
	
	@Test
	void testUpdateWithInvalidData() throws Exception {
		Mapping dto = new Mapping();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "mapping.pattern\"]}"));
	}
	
	@Test
	void testUpdate() throws Exception {
		Mapping dto = new Mapping();
		dto.setId(2L);
        dto.setPattern("/api/core/**");
        dto.setPosition((byte) 98);
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	void whenNameAlreadyExistsDuplicatedKeyExceptionIsThrown() throws Exception {
		Mapping dto = new Mapping();
		dto.setPattern("/api/core/**");
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(9L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isConflict());	
	}
	
	@Test
	void testDelete() throws Exception {
		Mapping dto = new Mapping();
		dto.setId(-2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_DELETE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void whenUserCantReadAccesDeniedIsThrown() throws Exception {
		Mapping dto = new Mapping();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000002");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void whenUserCantEditAccesDeniedIsThrown() throws Exception {
		Mapping dto = new Mapping();
		dto.setPattern("Test Mapping forbidden");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000001");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void testInitEdit() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				MappingConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MappingDataLoad> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MappingDataLoad>>() {});
		MappingDataLoad result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getMappings());
		assertNotNull(result.getAuthorities());
	}
	
	@Test
	void testValidate() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				MappingConstants.CONTROLLER_NAMESPACE + MappingConstants.MAPPING_VALIDATE);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Map<String, List<String>>> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Map<String, List<String>>>>() {});
		Map<String, List<String>> result = apiResponse.getResponse();
		assertNotNull(result);
		assertTrue(result.get("/api/mappings/validate").contains("Administrador"));
	}
}
