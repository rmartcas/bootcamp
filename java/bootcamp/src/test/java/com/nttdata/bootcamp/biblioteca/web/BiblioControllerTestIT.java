package com.nttdata.bootcamp.biblioteca.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.nttdata.bootcamp.biblioteca.model.Libro;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.crud.constants.CrudConstants;

class BiblioControllerTestIT extends BaseTest {
	
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void testFind() throws Exception {
		
		//Preparar
		Libro dto = new Libro();
		dto.setId(1L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/biblio" + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		// Ejecutar
		this.login(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		// Verificar
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Libro> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Libro>>() {});
		Libro result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals("IT", result.getTitulo());
		assertNotNull(result.getAutor());
	}
}
