/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.crud.mapper.CrudMapper;

/**
 * Auditable interface to persist auditable actions
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Mapper
public interface AuditableMapper extends CrudMapper<Audit> {
	
	static final String METHOD_NOT_ALLOWED = "Method not allowed";
	
	/**
	 * Find a list of auditable tables in current application
	 * 
	 * @return {@link List} of {@link Combo} with records
	 */
	List<Combo> findAuditTables();
	
	/**
	 * Find a record of {@link Audit}
	 * 
	 * @param dto the record to find
	 * @return {@link List} of {@link Map} with records
	 */
	List<Map<String, Object>> findByPairKey(Audit dto);
	
	@Override
	default void delete(Audit dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default void update(Audit dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default Audit find(Audit dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
}
