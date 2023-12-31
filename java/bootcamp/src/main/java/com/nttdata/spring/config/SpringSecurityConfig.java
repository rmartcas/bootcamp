/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hdiv.filter.ValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml4AuthenticationProvider;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.filter.RequestIdFilter;
import com.nttdata.core.context.ConfigProperties;
import com.nttdata.core.mappings.service.MappingService;
import com.nttdata.core.security.authentication.AuthenticationProviderWrapper;
import com.nttdata.core.security.authentication.CoreAuthenticationSuccessHandler;
import com.nttdata.core.security.authorization.DatabaseAuthorizationManager;
import com.nttdata.core.security.headers.CsrfTokenHeaderWriter;
import com.nttdata.core.security.listener.SecurityEventListener;
import com.nttdata.spring.config.constants.SpringConfigConstants;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class for spring security
 *
 * @author NTT DATA
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Slf4j
public class SpringSecurityConfig {
	
	/** The config properties*/
	@Autowired
	private ConfigProperties configProperties;
	
	/** The mappings service */
    @Autowired
    private MappingService mappingService;
    
	/**
	 * Http security config.
	 * @param http {@link HttpSecurity} the http security configurer
	 * @param endpointProperties {@link WebEndpointProperties} actuator endpoint properties to configure
	 * @return {@link SecurityFilterChain} the configured filter chain
	 * @throws Exception if error
	 */
    @SuppressWarnings("unchecked")
    @Bean
 	protected SecurityFilterChain filterChain(HttpSecurity http, WebEndpointProperties endpointProperties) throws Exception {
        CsrfTokenRepository csrfTokenRepository = csrfTokenRepository();
        http.authorizeHttpRequests()
        	.shouldFilterAllDispatcherTypes(true)
    		.withObjectPostProcessor(new RequestMatcherDelegatingAuthorizationManagerPostProcessor())
 			//Common pages must be last declared
 			.antMatchers(configProperties.getSecurity().getUrl().getLogout()).authenticated()
            .antMatchers(endpointProperties.getBasePath() + "/health").permitAll()
            .antMatchers(endpointProperties.getBasePath() + SpringConfigConstants.PATTERN_ALL).hasRole(SpringConfigConstants.Security.ACTUATOR_ROLE)
            .antMatchers("/swagger-ui" + SpringConfigConstants.PATTERN_ALL).authenticated()
 			//All other pages are denied by default if not role is assigned
 			.anyRequest().denyAll()
       		.and()
       		.saml2Login(saml2LoginCustomizer -> {
       			try {
                    saml2LoginCustomizer.loginProcessingUrl(Saml2WebSsoAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI);
                    
                    //Success handler to use after authentication
                    saml2LoginCustomizer.successHandler(authenticationSuccessHandler());
                    //Failed handler to use after failed authentication
                    saml2LoginCustomizer.failureHandler(authenticationFailureHandler());
				} catch (Exception e) {
					log.debug("No se pudo customizar el authenticationManager", e);
				}
       		})
 		
 		//Enable csrf protection (Enabled for all request except "GET", "TRACE", "HEAD" and "OPTIONS")
 		.csrf()
        .csrfTokenRepository(csrfTokenRepository)
 		
 		//Remove logout filter, not necessary
        .and().logout(logoutCustomizer -> {
        	String logoutUrl = configProperties.getSecurity().getUrl().getLogout();
            logoutCustomizer.logoutUrl(logoutUrl);
            logoutCustomizer.logoutSuccessHandler(successLogoutHandler());
            logoutCustomizer.invalidateHttpSession(true);
            logoutCustomizer.clearAuthentication(true);
            logoutCustomizer.logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl));
            
        })
 		
 		 //Force create a new 'JSESSIONID' after login.
 		.sessionManagement().sessionFixation().changeSessionId()
 		
 		.and()
 		//Default header for all responses
 		.headers()
            .contentSecurityPolicy("default-src 'self'; frame-ancestors 'self'; object-src 'none'; script-src 'self' 'unsafe-inline'; img-src 'self' data:")
 			.and()
            .referrerPolicy(ReferrerPolicy.STRICT_ORIGIN)
            .and()
 			.addHeaderWriter(csrfTokenHeaderWriter(csrfTokenRepository))
 			.frameOptions().sameOrigin()
 		
 		//Allow a basic configuration
 		.and().httpBasic()
        .and()
        	.addFilterBefore(new RequestIdFilter(), ChannelProcessingFilter.class)
        	.addFilterBefore(mdcInsertingServletFilter(), ChannelProcessingFilter.class)
        	.addFilterAfter(validatorFilter(), HeaderWriterFilter.class)
 		;
        
        //Disable anonymous access
        http.anonymous().disable();
 		
 		//Disable default login and logout page
 		http.removeConfigurer(DefaultLoginPageConfigurer.class);
 		
 		http.authenticationProvider(authenticationProviderWrapper());
 		
 		return http.build();
 	}
    
    /**
	 * Configure Hdiv validator filter for spring security
	 * @return Hdiv {@link ValidatorFilter} to use
	 */
	@Bean(name = "hdivFilter")
	public ValidatorFilter validatorFilter() {
		return new ValidatorFilter();
	}
    
    /**
 	 * Setup application password encoder.
 	 * @return passwordEncoder {@link PasswordEncoder}
 	 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    /**
 	 * Setup authentication provider in memory.
 	 * @return {@link InMemoryUserDetailsManager}
 	 */
    @Bean
    protected InMemoryUserDetailsManager configAuthentication() {
    	List<UserDetails> users = new ArrayList<>();

    	String user = System.getenv(SpringConfigConstants.Security.ACTUATOR_USERNAME_ENV);
        String pwd = System.getenv(SpringConfigConstants.Security.ACTUATOR_PASSWORD_ENV);
        if (null != StringUtils.trimToNull(user) && null != StringUtils.trimToNull(pwd)) {
        	List<GrantedAuthority> actuatorAuthority = new ArrayList<>();
            actuatorAuthority.add(new SimpleGrantedAuthority("ROLE_" + SpringConfigConstants.Security.ACTUATOR_ROLE));
            UserDetails admin = new User(user, passwordEncoder().encode(pwd), actuatorAuthority);
            users.add(admin);
        }
        return new InMemoryUserDetailsManager(users);
    }
 	
 	/**
 	 * Setup a new {@link AuthenticationProviderWrapper}.
 	 * 
 	 * It wraps the provider and user details to use for authentication.
 	 * @return {@link AuthenticationProviderWrapper}
 	 */
	@Bean
	public AuthenticationProviderWrapper authenticationProviderWrapper() {
		AuthenticationProviderWrapper wrapper = new AuthenticationProviderWrapper();
 		wrapper.setProvider(openSamlAuthenticationProvider());
		return wrapper;
	}
	
	/**
 	 * Setup a new {@link OpenSaml4AuthenticationProvider}.
 	 * @return {@link OpenSaml4AuthenticationProvider}
 	 */
 	@Bean
 	public AuthenticationProvider openSamlAuthenticationProvider() {
        return new OpenSaml4AuthenticationProvider();
	}
    
    
    /**
 	 * Create new {@link AuthenticationSuccessHandler}
 	 * @return {@link CoreAuthenticationSuccessHandler}
 	 */
 	@Bean(name = "authenticationSuccessHandler")
 	public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CoreAuthenticationSuccessHandler successHandler = new CoreAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl(CommonConstants.FORWARD_SLASH);
       return successHandler;
	}
    
    /**
     * Handler deciding where to redirect user after failed login
     * @return {@link SimpleUrlAuthenticationFailureHandler}
     */
    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
	    	return new SimpleUrlAuthenticationFailureHandler();
    }
     
    /**
     * Handler for successful logout
     * @return {@link SimpleUrlLogoutSuccessHandler}
     */
    @Bean
    public SimpleUrlLogoutSuccessHandler successLogoutHandler() {
        SimpleUrlLogoutSuccessHandler successLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        successLogoutHandler.setDefaultTargetUrl(configProperties.getSecurity().getUrl().getLogoutIdp());
        return successLogoutHandler;
    }
    
    @Bean
    public SecurityEventListener securityEventListerner() {
    	return new SecurityEventListener();
    }

    @Bean
	public MDCInsertingServletFilter mdcInsertingServletFilter() {   
	    return new MDCInsertingServletFilter();    
	}
    
    private CsrfTokenRepository csrfTokenRepository() {
    	return new HttpSessionCsrfTokenRepository();
	}
    
    private HeaderWriter csrfTokenHeaderWriter(CsrfTokenRepository tokenRepository) {
		return new CsrfTokenHeaderWriter(tokenRepository);
	}
 	
    class RequestMatcherDelegatingAuthorizationManagerPostProcessor implements ObjectPostProcessor<AuthorizationManager<HttpServletRequest>> {

		@SuppressWarnings("unchecked")
		@Override
		public <O extends AuthorizationManager<HttpServletRequest>> O postProcess(O object) {
			return (O) new DatabaseAuthorizationManager(mappingService, object);
		}
    }
}
