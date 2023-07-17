/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.users.web;

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
import com.nttdata.core.users.constants.UserConstants;
import com.nttdata.core.users.model.UserDataLoad;
import com.nttdata.core.users.model.User;
import com.nttdata.core.users.model.UserPage;
import com.nttdata.core.profiles.model.Profile;

/**
 * Test users module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class UserControllerTestIT extends BaseTest {

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
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		addData(requestBuilder, rb);
	}

	private void addInitialData(MockHttpServletRequestBuilder requestBuilder, String login) throws Exception {
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.get(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		addData(requestBuilder, rb);
	}
	
	@Test
	void testSearch() throws Exception {
		UserPage dto = new UserPage();
		dto.setFilters(new User());
		dto.getFilters().setName("manager");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<UserPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<UserPage>>() {});
		UserPage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(1, result.getRecords().size());
	}
	
	@Test
	void testSearchWithEmptyResults() throws Exception {
		UserPage dto = new UserPage();
		dto.setFilters(new User());
		dto.getFilters().setName("notfoundname");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<UserPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<UserPage>>() {});
		UserPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(0, result.getRecords().size());
	}
	
	@Test
	void testSearchWithOrder() throws Exception {
		UserPage dto = new UserPage();
		
		List<PageOrder> orders = new ArrayList<>();
		orders.add(new PageOrder("filters.name", Direction.ASC));
		orders.add(new PageOrder("filters.email", Direction.ASC));
		orders.add(new PageOrder("notExist", Direction.ASC));
		
		dto.setPageOrder(orders);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<UserPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<UserPage>>() {});
		UserPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
	}
	
	@Test
	void testSearchWithInvalidData() throws Exception {
		UserPage dto = new UserPage();
		dto.setFilters(new User());
		dto.getFilters().setName("< invalid char");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"" + I18nConstants.I18N_PREFIX + "userPage.filters.name\"]}"));
	}
	
	@Test
	void testFind() throws Exception {
		User dto = new User();
		dto.setId(-2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<User> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<User>>() {});
		User result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(-2L), result.getId());
		assertEquals("user1", result.getName());
		assertNotNull(result.getProfile());
		assertEquals(Long.valueOf(-2L), result.getProfile().getId());
	}
	
	@Test
	void testInsert() throws Exception {
		User dto = new User();
		dto.setUsername("new_user");
		dto.setName("Test User 1");
		dto.setEmail("newUserEmail@bootcamp.com");
		Profile profile = new Profile();
		profile.setId(-2L);
		dto.setProfile(profile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<User> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<User>>() {});
		User result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	@Test
	void testInsertWithInvalidData() throws Exception {
		User dto = new User();
		dto.setName("Test User 2");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "user.username\"]}"));
	}
	
	@Test
	void testUpdateWithInvalidData() throws Exception {
		User dto = new User();
		dto.setId(2L);
		dto.setName("Updated User");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "user.username\"]}"));
	}
	
	@Test
	void testUpdate() throws Exception {
		User dto = new User();
		dto.setId(-4L);
		dto.setName("Updated User");
		dto.setUsername("UpdateUser");
		dto.setEmail("updated_email@bootcamp.com");
		Profile profile = new Profile();
		profile.setId(-2L);
		dto.setProfile(profile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	void whenNameAlreadyExistsDuplicatedKeyExceptionIsThrown() throws Exception {
		User dto = new User();
		dto.setUsername("E000001");
		dto.setName("Test User 1");
		dto.setEmail("newUserEmail@bootcamp.com");
		Profile profile = new Profile();
		profile.setId(-2L);
		dto.setProfile(profile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isConflict());	
	}
	
	@Test
	void testDelete() throws Exception {
		User dto = new User();
		dto.setId(7L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_DELETE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void whenUserCantReadAccesDeniedIsThrown() throws Exception {
		User dto = new User();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000002");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void whenUserCantEditAccesDeniedIsThrown() throws Exception {
		User dto = new User();
		dto.setUsername("update_user");
		dto.setName("Test User 1");
		dto.setEmail("newUserEmail@bootcamp.com");
		Profile profile = new Profile();
		profile.setId(2L);
		dto.setProfile(profile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000001");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void testInit() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<UserDataLoad> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<UserDataLoad>>() {});
		UserDataLoad result = apiResponse.getResponse();
		assertNotNull(result);
		assertFalse(result.getProfiles().isEmpty());
	}
	
	@Test
	void testInitEdit() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				UserConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<UserDataLoad> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<UserDataLoad>>() {});
		UserDataLoad result = apiResponse.getResponse();
		assertNotNull(result);
		assertFalse(result.getProfiles().isEmpty());
	}
}
