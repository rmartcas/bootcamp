/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.security.recaptcha.service;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import com.nttdata.BaseTest;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.security.recaptcha.annotation.Recaptcha;
import com.nttdata.core.security.recaptcha.exception.InvalidRecaptchaException;


/**
 * Recaptcha service test class
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
class RecaptchaServiceTestIT extends BaseTest {
	
	@Autowired
	private ConfigProperties configProperties;
	
	@SpyBean
	private RestOperations restTemplate;
	
	@SpyBean
	private RecaptchaService recaptchaService;
	
	@Captor
	ArgumentCaptor<String> tokenCaptor;

	@Captor
	ArgumentCaptor<String> actionCaptor;
	
	
	@Test
	void testInvalidRecaptchaHeader() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1");
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertTrue(resolvedException instanceof InvalidRecaptchaException);
		Assertions.assertEquals("Response contains invalid characters", resolvedException.getMessage());
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertNull(tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
	@Test
	void testInvalidRecaptchaToken() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1").header("g-recaptcha-response", "12313aaa.aaaaa");
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertTrue(resolvedException instanceof InvalidRecaptchaException);
		Assertions.assertEquals("Response contains invalid characters", resolvedException.getMessage());
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("12313aaa.aaaaa", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
	@Test
	void testInvalidVerifyUrlCall() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		Mockito.doThrow(new RuntimeException())
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertNull(resolvedException);
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
	@Test
	void testInvalidRecaptchaResponse() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		com.nttdata.core.security.recaptcha.model.Recaptcha response = new com.nttdata.core.security.recaptcha.model.Recaptcha();
		response.setErrorCodes(new ArrayList<>());
		Mockito.doReturn(response)
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertTrue(resolvedException instanceof InvalidRecaptchaException);
		Assertions.assertTrue(resolvedException.getMessage().contains("reCaptcha was not successfully validated"));
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
	@Test
	void testInvalidRecaptchaResponseAction() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		com.nttdata.core.security.recaptcha.model.Recaptcha response = new com.nttdata.core.security.recaptcha.model.Recaptcha();
		response.setErrorCodes(new ArrayList<>());
		response.setAction("other-action");
		response.setSuccess(true);
		Mockito.doReturn(response)
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertTrue(resolvedException instanceof InvalidRecaptchaException);
		Assertions.assertTrue(resolvedException.getMessage().contains("reCaptcha response action does not match the required action"));
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
	@Test
	void testApplyGlobalScoreInValidation() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		com.nttdata.core.security.recaptcha.model.Recaptcha response = new com.nttdata.core.security.recaptcha.model.Recaptcha();
		response.setErrorCodes(new ArrayList<>());
		response.setAction("test-method-action");
		response.setSuccess(true);
		Mockito.doReturn(response)
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertTrue(resolvedException instanceof InvalidRecaptchaException);
		Assertions.assertEquals(String.format("reCaptcha response threshold (%.1f) is less than required (%.1f)", 0f, 0.5f), resolvedException.getMessage());
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
	@Test
	void testApplyCustomScoreInValidation() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m2").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		com.nttdata.core.security.recaptcha.model.Recaptcha response = new com.nttdata.core.security.recaptcha.model.Recaptcha();
		response.setErrorCodes(new ArrayList<>());
		response.setAction("test-controller-action");
		response.setSuccess(true);
		Mockito.doReturn(response)
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertTrue(resolvedException instanceof InvalidRecaptchaException);
		Assertions.assertEquals(String.format("reCaptcha response threshold (%.1f) is less than required (%.1f)", 0f, 0.8f), resolvedException.getMessage());
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-controller-action", actionCaptor.getValue());
	}
	
	@Test
	void tesSkipValidationForCustomAction() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m3").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		com.nttdata.core.security.recaptcha.model.Recaptcha response = new com.nttdata.core.security.recaptcha.model.Recaptcha();
		response.setErrorCodes(new ArrayList<>());
		response.setAction("test-method-action-skipped");
		response.setSuccess(true);
		Mockito.doReturn(response)
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertNull(resolvedException);
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action-skipped", actionCaptor.getValue());
	}
	
	@Test
	void tesWhenCaptchaIsDisabledNoValidationIsPerformed() throws Exception {
		configProperties.getSecurity().getRecaptcha().setEnabled(false);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m3").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
				
		Mockito.verify(recaptchaService, Mockito.times(0)).validate(tokenCaptor.capture(), actionCaptor.capture());
		configProperties.getSecurity().getRecaptcha().setEnabled(true);
	}
	
	@Test
	void testWhenResponseScoreIsHigherThanExpectedNoExceptionIsRaised() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/test/m1").header("g-recaptcha-response", "1231");
		
		this.login(requestBuilder, "E000000");

		com.nttdata.core.security.recaptcha.model.Recaptcha response = new com.nttdata.core.security.recaptcha.model.Recaptcha();
		response.setErrorCodes(new ArrayList<>());
		response.setAction("test-method-action");
		response.setSuccess(true);
		response.setScore(1.0f);
		Mockito.doReturn(response)
			.when(restTemplate).postForObject(Mockito.anyString(), Mockito.any(MultiValueMap.class), Mockito.any());
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		Exception resolvedException = ra.andReturn().getResolvedException();
		Assertions.assertNull(resolvedException);
		
		Mockito.verify(recaptchaService).validate(tokenCaptor.capture(), actionCaptor.capture());
		Assertions.assertEquals("1231", tokenCaptor.getValue());
		Assertions.assertEquals("test-method-action", actionCaptor.getValue());
	}
	
}

@Recaptcha(action = "test-controller-action")
@RestController
@RequestMapping("/api/test")
class RecapthaTestController {
	
	@Recaptcha(action = "test-method-action")
	@GetMapping(value = "/m1", produces = MediaType.APPLICATION_JSON_VALUE)
	public void method1() throws CoreException {
	}
	
	@GetMapping(value = "/m2", produces = MediaType.APPLICATION_JSON_VALUE)
	public void method2() throws CoreException {
	}
	
	@Recaptcha(action = "test-method-action-skipped")
	@GetMapping(value = "/m3", produces = MediaType.APPLICATION_JSON_VALUE)
	public void method3() throws CoreException {
	}
}
