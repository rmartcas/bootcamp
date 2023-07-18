/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.ui.service;

import java.util.List;

import com.nttdata.core.common.model.Button;
import com.nttdata.core.common.model.TableColumn;

/**
 * Generic service interface that implements methods to retrieve
 * UI elements like buttons and table columns based on user permissions.<br>
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface UIService {
	
	/**
	 * Gets the columns for UI.
	 * @param moduleFile {@link String} the module name
	 * @return a {@link List} of {@link TableColumn}
	 */
	List<TableColumn> getColumns(String moduleFile);
	
	/**
	 * Gets the buttons for UI.
	 * @param moduleFile {@link String} the module name
	 * @return a {@link List} of {@link Button}
	 */
	List<Button> getButtons(String moduleFile);	
}
