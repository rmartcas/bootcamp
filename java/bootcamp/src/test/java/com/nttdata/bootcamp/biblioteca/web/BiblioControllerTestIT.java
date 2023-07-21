package com.nttdata.bootcamp.biblioteca.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.BaseTest;
import com.nttdata.bootcamp.biblioteca.model.Autor;
import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.crud.constants.CrudConstants;

public class BiblioControllerTestIT extends BaseTest{

	private ObjectMapper mapper = new ObjectMapper();
	
//	@Test
//	void testSearch() throws Exception {
//		AuthorityPage dto = new AuthorityPage();
//		dto.setFilters(new Authority());
//		dto.getFilters().setName("READ_USERS");
//
//		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
//				AuthorityConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
//			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
//		
//
//		ResultActions ra = mockMvc.perform(requestBuilder);
//
//		ra.andExpect(MockMvcResultMatchers.status().isOk());
//		
//		ApiResponse<AuthorityPage> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<AuthorityPage>>() {});
//		AuthorityPage result = apiResponse.getResponse();
//		assertNotNull(result);
//		assertEquals(10, result.getSize());
//		assertEquals(1, result.getCurrentPage());
//		assertEquals(1, result.getRecords().size());
//	}
	
	@Test
	void testInsert() throws Exception {
		Libro libro = new Libro();
		libro.setTitulo("Titulo");
		Autor autor = new Autor();
		autor.setId(1L);
		libro.setAutor(autor);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/biblio" + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(libro));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Libro> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Libro>>() {});
		Libro result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
}
