/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

import com.nttdata.bootcamp.ApplicationConfig;

/**
 * bootcamp main class.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@SpringBootApplication(exclude = {
		AopAutoConfiguration.class,
		PersistenceExceptionTranslationAutoConfiguration.class,
		JdbcRepositoriesAutoConfiguration.class,
		SpringDataWebAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class,
		JacksonAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class,
		OAuth2ResourceServerAutoConfiguration.class,
		SecurityAutoConfiguration.class,
		TaskExecutionAutoConfiguration.class,
		TaskSchedulingAutoConfiguration.class,
		ValidationAutoConfiguration.class,
		ErrorMvcAutoConfiguration.class
})
@Import({ApplicationConfig.class})
public class Application {

	/**
	 * Initialize the application.
	 * 
	 * @param args {@link String} application arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}