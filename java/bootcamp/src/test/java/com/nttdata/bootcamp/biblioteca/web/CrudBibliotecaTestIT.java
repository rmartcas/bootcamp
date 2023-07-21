package com.nttdata.bootcamp.biblioteca.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.nttdata.core.authorities.constants.AuthorityConstants;
import com.nttdata.core.authorities.model.Authority;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.crud.constants.CrudConstants;

public class CrudBibliotecaTestIT extends BaseTest {

	
	
	private ObjectMapper mapper = new ObjectMapper();
	

	@Test
	void insert() throws Exception {
		Libro libro = new Libro();
		libro.setTitulo("Titulo");
		Autor autor = new Autor();
		autor.setNombre("Pedro");
		autor.setId(1l);
		libro.setAutor(autor);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/biblio" + CrudConstants.CRUD_CONTROLLER_INSERT)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(libro));
		this.login(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Libro> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Libro>>() {});
		Libro result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	@Test
	void delete() throws Exception {
		Libro libro = new Libro();
		libro.setId(-1L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/biblio" + CrudConstants.CRUD_CONTROLLER_DELETE )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(libro));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void testFind() throws Exception {
		Libro libro = new Libro();
		libro.setId(1L);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/biblio" + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(libro));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Libro> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Libro>>() {});
		Libro result = apiResponse.getResponse();
		assertNotNull(result);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals("IT", result.getTitulo());
		assertEquals(Long.valueOf(1L), result.getAutor().getId());
		assertEquals("Stephen King", result.getAutor().getNombre());
	}
}
