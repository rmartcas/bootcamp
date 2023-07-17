/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.model;

import java.util.List;
import java.io.Serializable;

import com.nttdata.core.common.model.Button;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.TableColumn;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class to store the initial data loaded in services
 * 
 * @see com.nttdata.core.common.service.Initializable#init(Page)
 * @see com.nttdata.core.common.service.Initializable#init(Core)
 * 
 * @author NTT DATA
 * @version 0.0.1
 */
@Getter
@Setter
public abstract class CrudDataLoad implements DataLoad, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * A list of buttons to show in the front page
	 * @param buttons {@link List} of {@link String} The butttons to use
	 * @return {@link List} of {@link String} The butttons to use
	 */
	private List<Button> buttons;
	
	/** 
	 * A list of columns to show in the front page
	 * @param columns {@link List} of {@link String} The columns to use
	 * @return {@link List} of {@link String} The columns to use
	 */
	private List<TableColumn> columns;
}
