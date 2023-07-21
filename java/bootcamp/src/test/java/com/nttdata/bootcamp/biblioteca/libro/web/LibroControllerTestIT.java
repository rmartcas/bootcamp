package com.nttdata.bootcamp.biblioteca.libro.web;

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
import com.nttdata.bootcamp.biblioteca.autor.model.Autor;
import com.nttdata.bootcamp.biblioteca.libro.model.Libro;
import com.nttdata.core.common.model.ApiResponse;
import com.nttdata.core.crud.constants.CrudConstants;

public class LibroControllerTestIT extends BaseTest{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testInsert() throws Exception {
		Libro dto = new Libro();
		dto.setTitulo("Test Libro 1");
		Autor autor=new Autor();
		autor.setNombre("AutorTest1");
		autor.setId(11L);
		dto.setAutor(autor);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/libro" + CrudConstants.CRUD_CONTROLLER_INSERT )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		
		ApiResponse<Libro> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Libro>>() {});
		Libro result = apiResponse.getResponse();
		assertNotNull(result);
		assertNotNull(result.getId());
	}
	
	@Test
	void testFind() throws Exception {
		Libro dto = new Libro();
		dto.setId(22L);
		Autor autor= new Autor();
		autor.setId(11L);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/libro" + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		ApiResponse<Libro> apiResponse = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiResponse<Libro>>() {});
		Libro result = apiResponse.getResponse();
		assertNotNull(result);
	//	assertEquals(Long.valueOf(22L), result.getId());
	//	assertEquals("IT", result.getTitulo());

	}
//	@Test
//	void testDelete() throws Exception {
//		Libro dto = new Libro();
//		dto.setId(-1L);
//		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
//				"/libro" + CrudConstants.CRUD_CONTROLLER_DELETE )
//				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
//		
//	
//
//		ResultActions ra = mockMvc.perform(requestBuilder);
//		ra.andExpect(MockMvcResultMatchers.status().isNoContent());
//	}

}
