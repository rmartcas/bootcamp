/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.model;

import java.util.List;

import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.model.CrudDataLoad;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Audit data loaded for search queries
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AuditDataLoad extends CrudDataLoad {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The allowed tables.
	 * @param tables {@link List} of {@link Combo} The tables
	 * @return {@link List} of {@link Combo} the tables
	 */
	private List<Combo> tables;
	
	/** 
	 * The allowed actions.
	 * @param actions {@link List} of {@link Combo} The actions
	 * @return {@link List} of {@link Combo} the actions
	 */
	private List<Combo> actions;

}
