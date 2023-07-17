/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity to represent a table column in the front app
 * 
 * @see com.nttdata.core.common.service.Initializable#init(Page)
 * @see com.nttdata.core.common.service.Initializable#init(Core)
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TableColumn implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The name of the column.
	 * @param name {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
	/** 
	 * The bind property within the model.
	 * @param prop {@link String} The bind property model name
	 * @return {@link String} the bind property model name
	 */
	private String prop;
	
	/** 
	 * The bind property within the model using for sorting.
	 * @param propertyOrder {@link String} The property name
	 * @return {@link String} the property name 
	 */
	private String propertyOrder;
	
	/** 
	 * The cell template defined in the front app to use.
	 * @param cellTemplate {@link String} The cell template
	 * @return {@link String} the cell template
	 */
	private String cellTemplate;
	
	/** 
	 * Is the column hidden?.
	 * @param hidden {@link Boolean} The hidden state
	 * @return {@link Boolean} the hidden state
	 */
	private boolean hidden;
	
	/** 
	 * Is the column a handler for collapse or expand a tree table?.
	 * @param treeColumn {@link Boolean} The tree column state
	 * @return {@link Boolean} the tree column state
	 */
	private boolean treeColumn;

}
