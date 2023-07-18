/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.service;

import java.util.List;

import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;

/**
 * Combo service of application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface ComboService {

	/**
	 * Searh data for combo.
	 * 
	 * @param comboPage {@link ComboPage} the combo to search
	 * @return {@link List}&lt;{@link Combo}&gt; A list of combo objects
	 */
	List<Combo> search(ComboPage comboPage);
	
	/**
	 * Searh data for combo in property files instead of datasource.
	 * 
	 * @param comboPage {@link ComboPage} the combo to search
	 * @param searchInProperties {@link Boolean} if true perform a search in property files
	 * @return {@link List}&lt;{@link Combo}&gt; A list of combo objects
	 */
	List<Combo> search(ComboPage comboPage, boolean searchInProperties);
}
