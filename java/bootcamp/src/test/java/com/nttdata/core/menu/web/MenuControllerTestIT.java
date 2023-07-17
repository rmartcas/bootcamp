/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.menu.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.nttdata.core.menu.constants.MenuConstants;
import com.nttdata.core.menu.model.MenuDataLoad;
import com.nttdata.core.menu.model.Menu;
import com.nttdata.core.menu.model.MenuPage;
import com.nttdata.core.authorities.model.Authority;

/**
 * Test menu module
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class MenuControllerTestIT extends BaseTest {

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
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		addData(requestBuilder, rb);
	}

	private void addInitialData(MockHttpServletRequestBuilder requestBuilder, String login) throws Exception {
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.get(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT);
		addData(requestBuilder, rb);
	}
	
	@Test
	void testSearch() throws Exception {
		MenuPage dto = new MenuPage();
		dto.setFilters(new Menu());
		dto.getFilters().setTitle("adminis");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MenuPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MenuPage>>() {});
		MenuPage result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(7, result.getRecords().size());
	}
	
	@Test
	void testSearchWithEmptyResults() throws Exception {
		MenuPage dto = new MenuPage();
		dto.setFilters(new Menu());
		dto.getFilters().setTitle("notfoundname");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MenuPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MenuPage>>() {});
		MenuPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
		assertEquals(0, result.getRecords().size());
	}
	
	@Test
	void testSearchWithOrder() throws Exception {
		MenuPage dto = new MenuPage();
		
		List<PageOrder> orders = new ArrayList<>();
		orders.add(new PageOrder("filters.title", Direction.ASC));
		orders.add(new PageOrder("filters.link", Direction.ASC));
		orders.add(new PageOrder("filters.enabled", Direction.ASC));
		orders.add(new PageOrder("notExist", Direction.ASC));
		
		dto.setPageOrder(orders);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MenuPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MenuPage>>() {});
		MenuPage result = apiResponse.getResponse();
		
		assertNotNull(result);
		assertEquals(10, result.getSize());
		assertEquals(1, result.getCurrentPage());
	}
	
	@Test
	void testSearchWithInvalidData() throws Exception {
		MenuPage dto = new MenuPage();
		dto.setFilters(new Menu());
		dto.getFilters().setTitle("< invalid char");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"" + I18nConstants.I18N_PREFIX + "menuPage.filters.title\"]}"));
	}
	
	@Test
	void testFind() throws Exception {
		Menu dto = new Menu();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Menu> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Menu>>() {});
		Menu result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(2L), result.getId());
		assertEquals("menu.administration.authorities", result.getTitle());
		assertNotNull(result.getAuthorities());
		assertFalse(result.getAuthorities().isEmpty());
	}
	
	@Test
	void testInsert() throws Exception {
		Menu dto = new Menu();
        dto.setTitle("new.menu.title");
        dto.setLink("new_link");
        dto.setPosition((byte) 99);
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(1L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Menu> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Menu>>() {});
		Menu result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	
	
	@Test
	void testInsertWithInvalidData() throws Exception {
		Menu dto = new Menu();
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "menu.title\"]}"));
	}
	
	@Test
	void testUpdateWithInvalidData() throws Exception {
		Menu dto = new Menu();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_REQUIRED + "\":[\"" + I18nConstants.I18N_PREFIX + "menu.title\"]}"));
	}
	
	@Test
	void testUpdate() throws Exception {
		Menu dto = new Menu();
		dto.setId(2L);
        dto.setTitle("menu.administration.profiles.updated");
        dto.setLink("updated_link");
        dto.setPosition((byte) 98);
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(9L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	void testUpdateParentIsSameAsId() throws Exception {
		Menu dto = new Menu();
		dto.setId(2L);
		dto.setParent(new Menu());
		dto.getParent().setId(2L);
        dto.setTitle("menu.administration.profiles.updated");
        dto.setLink("updated_link");
        dto.setPosition((byte) 98);
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(9L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		assertTrue(ra.andReturn().getResponse().getContentAsString().contains("{\"" + I18nConstants.I18N_VALIDATION_FIELD_INVALID + "\":[\"" + I18nConstants.I18N_PREFIX + "menu.parent\"]}"));
	}
	
	@Test
	void whenNameAlreadyExistsDuplicatedKeyExceptionIsThrown() throws Exception {
		Menu dto = new Menu();
		dto.setTitle("newTitle");
		dto.setLink("profiles");
		dto.setAuthorities(new ArrayList<>());
		Authority auth = new Authority();
		auth.setId(9L);
		dto.getAuthorities().add(auth);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.addInitialEditData(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isConflict());	
	}
	
	@Test
	void testDelete() throws Exception {
		Menu dto = new Menu();
		dto.setId(5L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_DELETE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void whenUserCantReadAccesDeniedIsThrown() throws Exception {
		Menu dto = new Menu();
		dto.setId(2L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000002");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void whenUserCantEditAccesDeniedIsThrown() throws Exception {
		Menu dto = new Menu();
		dto.setTitle("Test Menu forbidden");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000001");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void testInitEdit() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				MenuConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_EDIT);
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<MenuDataLoad> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<MenuDataLoad>>() {});
		MenuDataLoad result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getAuthorities());
		assertNotNull(result.getParents());
	}
}
