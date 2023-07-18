/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.core.combos.mapper.ComboMapper;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;
import com.nttdata.core.combos.service.ComboService;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.i18n.service.I18nService;

/**
 * Implementation of combo service
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class ComboServiceImpl implements ComboService {

	/** The combo mapper. */
	@Autowired
	private ComboMapper comboMapper;
	
	@Autowired
	private I18nService translateService;
	
	/**
	 * Searh data for combo.
	 * 
	 * @param comboPage {@link ComboPage} the combo to search
	 * @return {@link List}&lt;{@link Combo}&gt; A list of combo objects
	 */
	@Override
	public List<Combo> search(ComboPage comboPage) {
		return this.search(comboPage, false);
	}
	
	/**
	 * Searh data for combo in property files instead of datasource.
	 * 
	 * @param comboPage {@link ComboPage} the combo to search
	 * @param searchInProperties {@link Boolean} if true perform a search in property files
	 * @return {@link List}&lt;{@link Combo}&gt; A list of combo objects
	 */
	@Override
	public List<Combo> search(ComboPage comboPage, boolean searchInProperties) {
		if (searchInProperties) {
			return this.searchInProperties(comboPage);	
		} else {
			return comboMapper.search(comboPage);
		}
	}

	/**
	 * <p>Search in property files iterating through all properties with with key + index.
	 * Index is a 0 based number witch is incremented by 1 in every iteration
	 * while the translate service not return a empty string.</p>
	 * <ul> 
	 * <li>i18n.key.0 = true,True value</li>
	 * <li>i18n.key.1 = false,False value</li>
	 * </ul>
	 * <p>The previous example will return a list with 2 elements where the key is true|false and value "True|False value"</p>
	 * <ul> 
	 * <li>i18n.key.0 = Single value</li>
	 * <li>i18n.key.1 = Double value</li>
	 * </ul> 
	 * <p>The previous example will return a list with 2 elements where the key and the value are the same "Single|Double value"</p>
	 *	
	 * <p>This method only used {@link ComboPage}.key attribute to search</p>
	 *
	 * @param comboPage {@link ComboPage} the combo to search
	 * @return {@link List}&lt;{@link Combo}&gt; A list of combo objects
	 */
	protected List<Combo> searchInProperties(ComboPage comboPage) {
		List<Combo> combos = new ArrayList<>();
		for (int i = 0;; i++) {
			String key = comboPage.getKey() + CommonConstants.DOT + Integer.toString(i);
			String value = translateService.getTranslation(key);
		    if (StringUtils.isBlank(value)) {
		        break;
		    }
		    String[] keyValue = value.split(CommonConstants.COMMA);
		    Combo combo = new Combo();
		    combo.setId(keyValue[0]);
		    if (keyValue.length == 1) {
		    	combo.setName(keyValue[0]);
		    } else {
		    	combo.setName(keyValue[1]);
		    }
		    combos.add(combo);
	    }
		return combos;
	}
}
