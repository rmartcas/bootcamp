/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.audit.constants.AuditableConstants;
import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.audit.model.AuditPage;
import com.nttdata.core.audit.service.AuditableService;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;

@RestController
@RequestMapping(AuditableConstants.CONTROLLER_NAMESPACE)
class AuditableController implements CrudController<Audit, AuditPage> {

	@Autowired
	private AuditableService service;
	
	@Override
	public CrudService<Audit> getService() {
		return service;
	}

	@Override
	public Validator getValidator() {
		return new AuditValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new AuditPageValidator();
	}
	
	@Override
	public Validator getDeleteValidator() {
		//No data validation needed to delete a record
		return null;
	}
	
	@Override
	public ResponseEntity<Audit> insert(Audit dto, BindingResult bindingResult, Model model) throws CoreException {
		//Audit only must be registered at service and interceptor level not fron UI.
        throw new UnsupportedOperationException("Unable to insert an audit from here");
	}
	
	/**
	 * Find a record of {@link Audit}
	 * 
	 * @param dto the record to find
	 * @param bindingResult Binding result for error validation
	 * @param model The model
	 * @return {@link List} of {@link Map} with records registered before and after step
	 * @throws CoreException if error
	 */
	@PostMapping(value = AuditableConstants.REQUEST_MAPPING_FIND_BY_PAIR_KEY, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> findByPairKey(@RequestBody Audit dto, BindingResult bindingResult, Model model) throws CoreException {
		AuditPage page = new AuditPage();
		page.setFilters(dto);
		this.validate(this.getFindValidator(), dto, bindingResult, this.getService().init(page));
		List<Map<String, Object>> result = service.findByPairKey(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
