/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;

/**
 * Combo interface to get combos data for presentation layer
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface ComboMapper {

	/**
	 * Searh data for combo.
	 * 
	 * @param comboPage {@link ComboPage} the combo criteria to search
	 * @return {@link List}&lt;{@link Combo}&gt; A list of combo objects
	 */
	List<Combo> search(ComboPage comboPage);
}
