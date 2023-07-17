/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.profiles.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
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
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.profiles.constants.ProfileConstants;
import com.nttdata.core.profiles.model.Profile;
import com.nttdata.core.profiles.model.ProfilePage;

/**
 * Test profiles module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class ProfileControllerTestIT extends BaseTest {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testSearch() throws Exception {
		ProfilePage dto = new ProfilePage();
		dto.setFilters(new Profile());
		dto.getFilters().setName("admi");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<ProfilePage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<ProfilePage>>() {});
		ProfilePage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(1, result.getRecords().size());
	}
	
	@Test
	void testSearchWithEmptyResults() throws Exception {
		ProfilePage dto = new ProfilePage();
		dto.setFilters(new Profile());
		dto.getFilters().setName("notfoundname");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<ProfilePage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<ProfilePage>>() {});
		ProfilePage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(0, result.getRecords().size());
	}
	
	@Test
	void testSearchWithOrder() throws Exception {
		ProfilePage dto = new ProfilePage();
		
		List<PageOrder> orders = new ArrayList<>();
		orders.add(new PageOrder("filters.name", Direction.ASC));
		orders.add(new PageOrder("filters.description", Direction.ASC));
		orders.add(new PageOrder("notExist", Direction.ASC));
		
		dto.setPageOrder(orders);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<ProfilePage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<ProfilePage>>() {});
		ProfilePage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
	}
	
	@Test
	void testFind() throws Exception {
		Profile dto = new Profile();
		dto.setId(1L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Profile> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Profile>>() {});
		Profile result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals("Administrador", result.getName());
		assertNotNull(result.getAuthorities());
		assertFalse(result.getAuthorities().isEmpty());
	}
	
	@Test
	void testInsert() throws Exception {
		Profile dto = new Profile();
		dto.setName("Test profile 1");
		dto.setDescription("A profile for tests");
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Profile> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Profile>>() {});
		Profile result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	@Test
	void testInsertWithInvalidData() throws Exception {
		Profile dto = new Profile();
		dto.setName("Test profile 2");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "profile.description\"]}"));
	}
	
	@Test
	void testUpdateWithInvalidData() throws Exception {
		Profile dto = new Profile();
		dto.setId(2L);
		dto.setName("Updated profile");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "profile.description\"]}"));
	}
	
	@Test
	void testUpdate() throws Exception {
		Profile dto = new Profile();
		dto.setId(-3L);
		dto.setName("Updated profile");
		dto.setDescription("Updated profile description");
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	void whenNameAlreadyExistsDuplicatedKeyExceptionIsThrown() throws Exception {
		Profile dto = new Profile();
		dto.setName("Administrador");
		dto.setDescription("A duplicated profile for tests");
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isConflict());	
	}
	
	@Test
	void testDelete() throws Exception {
		Profile dto = new Profile();
		dto.setId(4L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_DELETE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void whenUserCantReadAccesDeniedIsThrown() throws Exception {
		Profile dto = new Profile();
		dto.setId(1L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000002");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void whenUserCantEditAccesDeniedIsThrown() throws Exception {
		Profile dto = new Profile();
		dto.setName("Test profile forbidden");
		dto.setDescription("A profile for tests forbidden");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000001");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void whenADefaultProfileIsSetupCantSetupAnotherDefaultProfile() throws Exception {
		Profile dto = new Profile();
		dto.setName("A default profile 1");
		dto.setDescription("A profile for tests");
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		dto.setDefault(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				ProfileConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isConflict());
	}
}
