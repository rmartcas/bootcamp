/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.service;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;

/**
 * Initializable interface
 * 
 * @param <T> Any object extending {@link Core}
 * @author NTT DATA
 * @since 0.0.1
 */
public interface Initializable<T extends Core<?>> {
	
	/**
	 * Prepare the data used when user access the search form.<br>
	 * Should populate buttons, columns and other elements to
	 * feed the front app according to the user permissions.
	 * 
	 * @param dto {@link Page} of {@link T} the page to initialize
	 * @return {@link DataLoad} the object with all data needed
	 */
	public default DataLoad init(Page<T> dto) {
		return null;
	}
	
	/**
	 * Prepare the data used when user access the new/edit form
	 * Should populate buttons, columns and other elements to
	 * feed the front app according to the user permissions.
	 * 
	 * @param dto {@link T} the dto to initialize
	 * @return {@link DataLoad} the object with all data needed
	 */
	public default DataLoad init(T dto) {
		return null;
	}

}
