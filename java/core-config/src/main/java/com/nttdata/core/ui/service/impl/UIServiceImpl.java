/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.ui.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nttdata.core.common.model.Button;
import com.nttdata.core.common.model.TableColumn;
import com.nttdata.core.security.utils.SecurityUtils;
import com.nttdata.core.ui.service.UIService;

import lombok.extern.slf4j.Slf4j;

/**
 * Generic UI service that implements methods to retrieve
 * UI elements like buttons and table columns based on user permissions.<br>
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
@Service
public class UIServiceImpl implements UIService {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Gets the columns for UI.
	 * @param moduleFile {@link String} the module name
	 * @return a {@link List} of {@link TableColumn}
	 */
	@Override
	public List<TableColumn> getColumns(String moduleFile) {
		InputStream is = this.getClass().getResourceAsStream("/columns/" + moduleFile + ".json");
		
		try {
			return this.objectMapper.readValue(is, new TypeReference<List<TableColumn>>() {});
		} catch (Exception e) {
			log.warn("Error al recuperar columnas del module {}", moduleFile, e);
		}

		return Collections.emptyList();
	}
	
	/**
	 * Gets the buttons for UI.
	 * @param moduleFile {@link String} the module name
	 * @return a {@link List} of {@link Button}
	 */
	@Override
	public List<Button> getButtons(String moduleFile) {
		InputStream is = this.getClass().getResourceAsStream("/buttons/" + moduleFile + ".json");
		
		try {
			Collection<String> authorities = SecurityUtils.getSessionUser().getAuthorities();
			List<Button> buttons = this.objectMapper.readValue(is, new TypeReference<List<Button>>() {});
			List<Button> toReturn = new ArrayList<>();
			checkPermissions(authorities, buttons, toReturn);
			return toReturn;
		} catch (Exception e) {
			log.warn("Error al recuperar botones del module {}", moduleFile, e);
		}

		return Collections.emptyList();
	}
	
	private void checkPermissions(Collection<String> authorities, List<Button> buttons, List<Button> toReturn) {
		for (Button button : buttons) {
			for (String authority : button.getRoles()) {
				if (authorities.contains(authority)) {
					toReturn.add(button);
					break;
				}
			}
		}
	}
}
