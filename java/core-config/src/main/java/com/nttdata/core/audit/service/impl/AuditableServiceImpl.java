/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.core.audit.AuditableActionsEnum;
import com.nttdata.core.audit.mapper.AuditableMapper;
import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.audit.model.AuditDataLoad;
import com.nttdata.core.audit.service.AuditableService;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.ui.service.UIService;

/**
 * Implementation of audit service
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class AuditableServiceImpl implements AuditableService {

	/** The audit mapper. */
	@Autowired
	private AuditableMapper auditMapper;
	
	/** The UI service */
	@Autowired
	private UIService uiService;
	
	@Override
	public CrudMapper<Audit> getMapper() {
		return this.auditMapper;
	}
	
	@Override
	public DataLoad init(Page<Audit> dto) {
		AuditDataLoad data = new AuditDataLoad();
		data.setTables(auditMapper.findAuditTables());
		
		List<Combo> actions = new ArrayList<>();
		for (AuditableActionsEnum action : AuditableActionsEnum.values()) {
			Combo combo = new Combo();
			combo.setId(action.toString());
			combo.setName(action.toString());
			actions.add(combo);
		}
		data.setActions(actions);
		data.setButtons(uiService.getButtons("auditable.init"));
		data.setColumns(uiService.getColumns("auditable.init"));
		return data;
	}
	
	/**
	 * Find a record of {@link Audit}
	 * 
	 * @param dto the record to find
	 * @return {@link List} of {@link Map} with records
	 * @throws CoreException in case of error
	 */
	@Override
	@SuppressWarnings(CommonConstants.UNCHECKED)
	public List<Map<String, Object>> findByPairKey(Audit dto) throws CoreException {
		List<Map<String, Object>> result = auditMapper.findByPairKey(dto);
		List<Map<String, Object>> lowerResult = new ArrayList<>();
		for (Map<String, Object> map : result) {
			lowerResult.add(new CaseInsensitiveMap(map));
		}
		return lowerResult;
	}
}
