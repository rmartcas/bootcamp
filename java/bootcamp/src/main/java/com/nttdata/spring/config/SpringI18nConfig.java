/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.spring.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.i18n.constants.I18nConstants;
import com.nttdata.spring.config.constants.SpringConfigConstants;

/**
 * Configuration class for spring i18n support
 *
 * @author NTT DATA
 * @since 0.0.1
 */
@Configuration
public class SpringI18nConfig {
	
	/**
	 * i18n resource files
	 */
	@Value(SpringConfigConstants.I18n.PROPERTIES_CONFIG_LOCATION) 
	private Resource[] propertiesResources;
		
	/**
	 * Read all i18n property files and create new messageSource with these files
	 * @return ResourceBundleMessageSource with i18n resources
	 * @throws IOException if not i18n found.
	 */
	@Bean(name = SpringConfigConstants.I18n.MESSAGE_SOURCE_BEAN_NAME)
	public ResourceBundleMessageSource messageSource() throws IOException  {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		
		List<String> basenames = new ArrayList<>();
		for (Resource i18nProperties : propertiesResources) {
			
			String file = i18nProperties.getURL().toString();
			file = file.replace(CommonConstants.FORWARD_SLASH, CommonConstants.DOT);
			String basename = StringUtils.join(I18nConstants.I18N_PREFIX, StringUtils.substringAfter(file, I18nConstants.I18N_PREFIX));
			if (!basename.contains(CommonConstants.UNDERSCORE)) {
				basenames.add(StringUtils.remove(basename, SpringConfigConstants.I18n.PROPERTIES_EXTENSION));
			}
		}
		resourceBundleMessageSource.setBasenames(basenames.toArray(new String[0]));
		resourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		return resourceBundleMessageSource;
	}
}
