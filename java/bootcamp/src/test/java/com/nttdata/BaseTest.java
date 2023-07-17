/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

import org.hdiv.filter.ValidatorFilter;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.nttdata.core.common.model.CoreDetails;

import com.nttdata.core.security.authentication.AuthenticationProviderWrapper;
import com.nttdata.bootcamp.ApplicationConfig;

/**
 * Base test for all application tests 
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@ContextConfiguration(classes = ApplicationConfig.class)
@SpringBootTest
@Testcontainers
public abstract class BaseTest {
	
    @Container
    private static final JdbcDatabaseContainer<?> sqlContainer = DatabaseContainer.getInstance();

	protected static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";

	/** The mockMvc to call endpoints */
	protected MockMvc mockMvc;

	/** Injected web application context */
	@Autowired
	protected WebApplicationContext context;
	
	/** spring security filter */
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	@Autowired
    private ValidatorFilter validatorFilter;
	
	@Autowired
	private AuthenticationProviderWrapper authenticationProviderWrapper;
	
	@Mock
	private AuthenticationProvider provider;
	
    @Mock
    private AuthenticationConverter tokenConverter;
	
	/**
	 * Initialize test mocks before each test method
	 * 
	 * @throws Exception en caso de error
	 */
	@BeforeEach
	void initMockWs() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain, validatorFilter)
				.build();
		
        DefaultSecurityFilterChain securityFilterChain = (DefaultSecurityFilterChain) springSecurityFilterChain.getFilterChains().get(0);
        for (Filter filter : securityFilterChain.getFilters()) {
            if (filter instanceof Saml2WebSsoAuthenticationFilter) {
                ReflectionTestUtils.setField(filter, "authenticationConverter", tokenConverter);
                break;
            }
        }
		ReflectionTestUtils.setField(authenticationProviderWrapper, "provider", provider);
	}
	
	/**
	 * Login for specified <code>username</code>
	 * 
	 * After login, the obtained session and authentication is injected in <code>requestBuilder</code> param
	 * for next calls.
	 * 
	 * @throws Exception en caso de error
	 */
	protected void login(MockHttpServletRequestBuilder requestBuilder, String username) throws Exception {		
		CoreDetails details2 = new CoreDetails(username, new HashMap<>(), new ArrayList<>() );
		details2.setRelyingPartyRegistrationId("test");
		Saml2Authentication result = new Saml2Authentication(details2, "samlResponse", new ArrayList<>());
		
		WebAuthenticationDetails details = new WebAuthenticationDetails(new MockHttpServletRequest());
		result.setDetails(details);

        Mockito.doReturn(result).when(tokenConverter).convert(Mockito.any(HttpServletRequest.class));
		Mockito.doReturn(result).when(provider).authenticate(Mockito.any(Authentication.class));
		Mockito.doReturn(true).when(provider).supports(Mockito.any(Class.class));

		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post(Saml2WebSsoAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI.replace("{registrationId}", "sso"))
				.param("SAMLResponse", "dummyResponse");

		ResultActions ra = mockMvc.perform(rb);

		String token = ra.andReturn().getResponse().getHeader(X_CSRF_TOKEN);

		rb = MockMvcRequestBuilders.post("/")
				.header(X_CSRF_TOKEN, token)
				.principal((Principal) result)
				.session((MockHttpSession) ra.andReturn().getRequest().getSession());
		
		ra = mockMvc.perform(rb);
		
		token = ra.andReturn().getResponse().getHeader(X_CSRF_TOKEN);
		requestBuilder
			.principal((Principal) result)
			.header(X_CSRF_TOKEN, token)
			.session((MockHttpSession) ra.andReturn().getRequest().getSession());
	}
}
