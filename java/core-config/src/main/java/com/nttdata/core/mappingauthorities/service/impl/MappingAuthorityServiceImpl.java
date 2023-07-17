/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappingauthorities.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.common.utils.BatchUtils;
import com.nttdata.core.mappingauthorities.mapper.MappingAuthorityMapper;
import com.nttdata.core.mappingauthorities.model.MappingAuthority;
import com.nttdata.core.mappingauthorities.service.MappingAuthorityService;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.authorities.model.Authority;

/**
 * Implementation of Mapping Authority service interface to handle authorities and mapping associations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class MappingAuthorityServiceImpl implements MappingAuthorityService {
	
	/** The associated mapper */
	@Autowired
	private MappingAuthorityMapper mappingAuthorityMapper;
	
	/** The SqlSessionTemplate to perform batch operations */
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * Inserts a list of authorities associated with the mapping
	 * 
	 * @param dto the mapping
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.INSERT, table = "core_mapping_authorities", primaryKey = "mapping_id")
	public void insertAuthorities(Mapping dto) {
		List<MappingAuthority> batchList = new ArrayList<>();
		
		if (null != dto.getAuthorities() && !dto.getAuthorities().isEmpty()) {
			for (Authority authority : dto.getAuthorities()) {
				batchList.add(createMappingAuthority(dto, authority));
			}
			BatchUtils.execute(sqlSessionTemplate, MappingAuthorityMapper.class.getCanonicalName() + ".insert", batchList);
		}
	}

	
	/**
	 * Delete all authorities associated with the mapping
	 * 
	 * @param dto the mapping
	 */
	@Override
	@Auditable(action = AuditableActionsEnum.DELETE, table = "core_mapping_authorities", primaryKey = "mapping_id")
	public void deleteAuthorities(Mapping dto) {
		MappingAuthority ma = new MappingAuthority();
		ma.getMapping().setId(dto.getId());
		mappingAuthorityMapper.deleteAuthoritiesByMapping(ma);
	}
	
	/**
	 * Create a new {@link MappingAuthority}
	 * 
	 * @param dto the mapping
	 * @param authority the authority
	 * @return {@link MappingAuthority}
	 */
	protected MappingAuthority createMappingAuthority(Mapping dto, Authority authority) {
		MappingAuthority ma = new MappingAuthority();
		ma.getMapping().setId(dto.getId());
		ma.getAuthority().setId(authority.getId());
		return ma;
	}
}
