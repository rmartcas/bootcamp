/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.authorities.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

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
import com.nttdata.core.authorities.constants.AuthorityConstants;
import com.nttdata.core.authorities.model.AuthorityDataLoad;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.authorities.model.AuthorityPage;
import com.nttdata.core.profiles.model.Profile;

/**
 * Test authorities module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class AuthorityControllerTestIT extends BaseTest {

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
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		addData(requestBuilder, rb);
	}

	private void addInitialData(MockHttpServletRequestBuilder requestBuilder, String login) throws Exception {
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.get(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		addData(requestBuilder, rb);
	}
	
	@Test
	void testSearch() throws Exception {
		AuthorityPage dto = new AuthorityPage();
		dto.setFilters(new Authority());
		dto.getFilters().setName("READ_USERS");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<AuthorityPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuthorityPage>>() {});
		AuthorityPage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(1, result.getRecords().size());
	}
	
	@Test
	void testSearchWithEmptyResults() throws Exception {
		AuthorityPage dto = new AuthorityPage();
		dto.setFilters(new Authority());
		dto.getFilters().setName("notfoundname");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<AuthorityPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuthorityPage>>() {});
		AuthorityPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(0, result.getRecords().size());
	}
	
	@Test
	void testSearchWithOrder() throws Exception {
		AuthorityPage dto = new AuthorityPage();
		
		List<PageOrder> orders = new ArrayList<>();
		orders.add(new PageOrder("filters.name", Direction.ASC));
		orders.add(new PageOrder("filters.description", Direction.ASC));
		orders.add(new PageOrder("notExist", Direction.ASC));
		
		dto.setPageOrder(orders);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<AuthorityPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuthorityPage>>() {});
		AuthorityPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
	}
	
	@Test
	void testSearchWithInvalidData() throws Exception {
		AuthorityPage dto = new AuthorityPage();
		dto.setFilters(new Authority());
		dto.getFilters().setName("< invalid char");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"" + I18nConstants.I18N_PREFIX + "authorityPage.filters.name\"]}"));
	}
	
	@Test
	void testFind() throws Exception {
		Authority dto = new Authority();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Authority> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Authority>>() {});
		Authority result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(2L), result.getId());
		assertEquals("READ_AUTHORITIES", result.getName());
		assertNotNull(result.getProfiles());
		assertFalse(result.getProfiles().isEmpty());
	}
	
	@Test
	void testInsert() throws Exception {
		Authority dto = new Authority();
		dto.setName("Test Authority 1");
		dto.setDescription("A Authority for tests");
		dto.setProfiles(new ArrayList<>());
		Profile profile = new Profile();
		profile.setId(-2L);
		dto.getProfiles().add(profile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Authority> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Authority>>() {});
		Authority result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	@Test
	void testInsertWithInvalidData() throws Exception {
		Authority dto = new Authority();
		dto.setName("Test Authority 2");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "authority.description\"]}"));
	}
	
	@Test
	void testUpdateWithInvalidData() throws Exception {
		Authority dto = new Authority();
		dto.setId(2L);
		dto.setName("Updated Authority");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "authority.description\"]}"));
	}
	
	@Test
	void testUpdate() throws Exception {
		Authority dto = new Authority();
		dto.setId(2L);
		dto.setName("Updated Authority");
		dto.setDescription("Updated Authority description");
		dto.setProfiles(new ArrayList<>());
		Profile profile = new Profile();
		profile.setId(-2L);
		dto.getProfiles().add(profile);
		profile = new Profile();
		profile.setId(1L);
		dto.getProfiles().add(profile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	void whenNameAlreadyExistsDuplicatedKeyExceptionIsThrown() throws Exception {
		Authority dto = new Authority();
		dto.setName("READ_USERS");
		dto.setDescription("A duplicated Authority for tests");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isConflict());	
	}
	
	@Test
	void testDelete() throws Exception {
		Authority dto = new Authority();
		dto.setId(-1L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_DELETE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void whenUserCantReadAccesDeniedIsThrown() throws Exception {
		Authority dto = new Authority();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000002");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void whenUserCantEditAccesDeniedIsThrown() throws Exception {
		Authority dto = new Authority();
		dto.setName("Test Authority forbidden");
		dto.setDescription("A Authority for tests forbidden");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000001");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void testInitEdit() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<AuthorityDataLoad> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuthorityDataLoad>>() {});
		AuthorityDataLoad result = apiResponse.getResponse();
		assertNotNull(result);
		assertFalse(result.getProfiles().isEmpty());
	}
}
