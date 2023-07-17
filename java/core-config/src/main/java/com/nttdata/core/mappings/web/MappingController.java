/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.crud.handler.ExcelExportableCursorHandler;
import com.nttdata.core.crud.handler.ExportableCursorHandler;
import com.nttdata.core.crud.service.CrudService;
import com.nttdata.core.crud.web.CrudController;
import com.nttdata.core.i18n.service.I18nService;
import com.nttdata.core.mappings.constants.MappingConstants;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.mappings.model.MappingPage;
import com.nttdata.core.mappings.service.MappingService;

/**
 * Controller to handle mappings
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@RestController
@RequestMapping(MappingConstants.CONTROLLER_NAMESPACE)
class MappingController implements CrudController<Mapping, MappingPage> {

	/** The associated service */
	@Autowired
	private MappingService service;
	
	@Autowired
	private I18nService i18nService;
		
	@Override
	public CrudService<Mapping> getService() {
		return service;
	}

	@Override
	public Validator getValidator() {
		return new MappingValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new MappingPageValidator();
	}
	
	@Override
	public Validator getDeleteValidator() {
		//No data validation needed to delete a record
		return null;
	}
	
	@Override
	public Validator getFindValidator() {
		//No data validation needed to find a record
		return null;
	}
	
	/**
	 * Returns a report with all applications url and the profiles that can access each one.
	 * 
	 * @return {@link Map}&lt;{@link String}, {@link List}&lt;{@link String}&gt;&gt; mapping access report
	 * @throws CoreException in case of error
	 */
	@GetMapping(value = MappingConstants.MAPPING_VALIDATE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<String>>> validateMappings() throws CoreException {
		Map<String, List<String>> result = this.service.validateMappings();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<InputStreamSource> export(MappingPage dto, BindingResult bindingResult)
			throws CoreException, IOException {
		// Overriden method to show how pass i18nService to excel export
		ExportableCursorHandler<Mapping> cursorHandler = new ExcelExportableCursorHandler<>(i18nService);
		return this.export(dto, bindingResult, cursorHandler);
	}
}
