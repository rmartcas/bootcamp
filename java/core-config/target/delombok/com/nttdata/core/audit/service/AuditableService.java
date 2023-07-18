/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.service;

import java.util.List;
import java.util.Map;

import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.crud.service.CrudService;

/**
 * Auditable service of application
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public interface AuditableService extends CrudService<Audit> {
	
	/**
	 * Find a record of {@link Audit}
	 * 
	 * @param dto the record to find
	 * @return {@link List} of {@link Map} with records
	 * @throws CoreException in case of error
	 */
	List<Map<String, Object>> findByPairKey(Audit dto) throws CoreException;
}
