/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.nttdata.core.common.model.Page;

/**
 * Mapping search filters
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MappingPage extends Page<Mapping> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

}
