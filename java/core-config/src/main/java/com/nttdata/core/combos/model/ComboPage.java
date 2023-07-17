/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.nttdata.core.common.model.Core;

/**
 * Combo entity used for search combo data
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ComboPage extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The combo table where retreive data.
	 * @param table {@link String} The table
	 * @return {@link String} the table
	 */
	private String table;
	
	/** 
	 * The table column used as key.
	 * @param key {@link String} The key
	 * @return {@link String} the key
	 */
	private String key;
	
	/** 
	 * The table column used as text.
	 * @param value {@link String} The value
	 * @return {@link String} the value
	 */
	private String value;
	
	/** 
	 * Join to perform within the main <code>table</code> to filter table contents.<br>
	 * 
	 * @param joins {@link List} of {@link ComboJoinTable} The joins
	 * @return {@link List} of {@link ComboJoinTable} the joins
	 */
	private List<ComboJoinTable> joins;
	
	/** 
	 * Criteria to filter table contents.<br>
	 * All criterias are appended with an "AND" operator.
	 * 
	 * @param criteria {@link List} of {@link ComboCriteria} The criteria
	 * @return {@link List} of {@link ComboCriteria} the criteria
	 */
	private List<ComboCriteria> criteria;
	
	/** 
	 * Column names used to group the results.<br>
	 * 
	 * @param groupBy {@link List} of {@link String} The group by column names
	 * @return {@link List} of {@link String} The group by column names
	 */
	private List<String> groupBy;
}
