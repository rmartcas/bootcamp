/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Custom serializer for {@link String}.
 * <p>Escape the malicious string characters using {@link HtmlUtils#htmlEscape(String)}</p>
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class EscapedStringSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(HtmlUtils.htmlEscape(value, StandardCharsets.UTF_8.toString()));
	}

}
