/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.web;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nttdata.core.common.exception.BadRequestException;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.crud.constants.CrudConstants;
import com.nttdata.core.crud.handler.ExcelExportableCursorHandler;
import com.nttdata.core.crud.handler.ExportableCursorHandler;
import com.nttdata.core.crud.service.CrudService;

/**
 * Generic controller interface that implements generic method for crud operations.
 * 
 * @param <T> Any object extending {@link Core}
 * @param <P> A object extending Page of T
 * @author NTT DATA
 * @since 0.0.1
 */
public interface CrudController<T extends Core<?>, P extends Page<T>> {
	
	/**
	 * Get the service class of this controller
	 * 
	 * @return {@link CrudService} inside the controller
	 */
	CrudService<T> getService();
	
	/**
	 * Get the entity validator for this controller<br>
	 * By default it will be used to validate incoming data from insert/update/delete methods.
	 * If you need a specific validator for each method (insert/update/delete) you must override
	 * {@link CrudController#getInsertValidator()}, {@link CrudController#getUpdateValidator()}
	 *  or {@link CrudController#getDeleteValidator()} respectively
	 * @return {@link Validator} that supports T record type
	 */
	Validator getValidator();
	
	/**
	 * Get the validator used to validate the insert method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getInsertValidator() {
		return getValidator();
	}
	
	/**
	 * Get the validator used to validate the update method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getUpdateValidator() {
		return getValidator();
	}
	
	/**
	 * Get the validator used to validate the delete method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getDeleteValidator() {
		return getValidator();
	}
	
	/**
	 * Get the validator used to validate the find method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getFindValidator() {
		return getValidator();
	}

	/**
	 * Get the search validator for this controller<br>
	 * It will be user to validate incoming data from search method
	 * @return {@link Validator} that supports {@link Page}&lt;T&gt; record type
	 */
	Validator getSearchValidator();
	
	/**
	 * Persist a new record of T 
	 * 
	 * @param dto the record to persist
	 * @param bindingResult Binding result for error validation
	 * @param model {@link Model} the model where retrieve the initial data
	 * @return T persisted record
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_INSERT, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<T> insert(@RequestBody T dto, BindingResult bindingResult, Model model) throws CoreException {
		this.validate(this.getInsertValidator(), dto, bindingResult, this.getService().init(dto));
		this.getService().insert(dto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	/**
	 * Updates an existing record 
	 * 
	 * @param dto the record to update
	 * @param bindingResult Binding result for error validation
	 * @param model {@link Model} the model where retrieve the initial data
	 * @return Void
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_UPDATE, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<Void> update(@RequestBody T dto, BindingResult bindingResult, Model model) throws CoreException {
		this.validate(this.getUpdateValidator(), dto, bindingResult, this.getService().init(dto));
		this.getService().update(dto);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	/**
	 * Delete an existing record
	 * 
	 * @param dto the record to delete
	 * @param bindingResult Binding result for error validation
	 * @param model {@link Model} the model where retrieve the initial data
	 * @return Void
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<Void> delete(@RequestBody T dto, BindingResult bindingResult, Model model) throws CoreException {
		this.validate(this.getDeleteValidator(), dto, bindingResult, this.getService().init(dto));
		this.getService().delete(dto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Find a record of T
	 * 
	 * @param dto the record to find
	 * @param bindingResult Binding result for error validation
	 * @param model {@link Model} the model where retrieve the initial data
	 * @return T record data
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_FIND, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<T> find(@RequestBody T dto, BindingResult bindingResult, Model model) throws CoreException {
		this.validate(this.getFindValidator(), dto, bindingResult, this.getService().init(dto));
		T result = this.getService().find(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Find all records of T
	 * 
	 * @param dto with search criteria
	 * @param bindingResult Binding result for error validation
	 * @param model {@link Model} the model where retrieve the initial data
	 * @return {@link Page} of T that meets the search criteria
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_SEARCH, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<Page<T>> search(@RequestBody P dto, BindingResult bindingResult, Model model) throws CoreException {
		this.validate(this.getSearchValidator(), dto, bindingResult, this.getService().init(dto));
		Page<T> result = this.getService().search(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Prepare the data used when user access the search form.
	 * Data retrieved is stored in model with name like "init"
	 * for later validation purposes.
	 * 
	 * Inherit controllers must setup {@link org.springframework.web.bind.annotation.SessionAttributes}
	 * with previous name in order to store the object in session.
	 * 
	 * @param model {@link Model} the model where store the initial data
	 * @return {@link Object} the object with all initial data
	 * @throws CoreException in case of error
	 */
	@GetMapping(value = CrudConstants.CRUD_CONTROLLER_INIT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<DataLoad> init(Model model) throws CoreException {
		DataLoad result = this.getService().init((P) null);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Prepare the data used when user access the search form.
	 * Data retrieved is stored in model with name like "init"
	 * for later validation purposes.
	 * 
	 * Inherit controllers must setup {@link org.springframework.web.bind.annotation.SessionAttributes}
	 * with previous name in order to store the object in session.
	 * 
	 * @param dto optional model to initialize
	 * @param model {@link Model} the model where store the initial data
	 * @return {@link Object} the object with all initial data
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_INIT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<DataLoad> init(@RequestBody(required = false) P dto, Model model) throws CoreException {
		DataLoad result = this.getService().init(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Prepare the data used when user access the new/edit form
	 * Data retrieved is stored in model with name like "initedit"
	 * for later validation purposes.
	 * 
	 * Inherit controllers must setup {@link org.springframework.web.bind.annotation.SessionAttributes}
	 * with previous name in order to store the object in session.
	 * 
	 * @param model {@link Model} the model where store the initial data
	 * @return {@link Object} the object with all initial data for new or edit form
	 * @throws CoreException in case of error
	 */
	@GetMapping(value = CrudConstants.CRUD_CONTROLLER_INIT_EDIT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<DataLoad> initEdit(Model model) throws CoreException {
		DataLoad result = this.getService().init((T) null);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Prepare the data used when user access the new/edit form
	 * Data retrieved is stored in model with name like "initedit"
	 * for later validation purposes.
	 * 
	 * Inherit controllers must setup {@link org.springframework.web.bind.annotation.SessionAttributes}
	 * with previous name in order to store the object in session.
	 * 
	 * @param dto optional model to initialize
	 * @param model {@link Model} the model where store the initial data
	 * @return {@link Object} the object with all initial data for new or edit form
	 * @throws CoreException in case of error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_INIT_EDIT, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<DataLoad> initEdit(@RequestBody(required = false) T dto, Model model) throws CoreException {
		DataLoad result = this.getService().init(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Export all records of T.
	 * 
	 * Default export is to xlsx file
	 * 
	 * @param dto with search criteria
	 * @param bindingResult Binding result for error validation
	 * @return {@link InputStreamSource} exported data of T that meets the search criteria
	 * @throws CoreException in case of error
	 * @throws IOException in case os IO access error
	 */
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_EXPORT,
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<InputStreamSource> export(@RequestBody P dto, BindingResult bindingResult) throws CoreException, IOException {
		return export(dto, bindingResult, new ExcelExportableCursorHandler<>());
	}
	
	default ResponseEntity<InputStreamSource> export(P dto, BindingResult bindingResult, ExportableCursorHandler<T> handler)
			throws CoreException, IOException {
		this.validate(this.getSearchValidator(), dto, bindingResult, this.getService().init(dto));
		InputStream result = this.getService().export(dto, handler);
		byte[] res = IOUtils.toByteArray(result);
		InputStreamSource resource = new InputStreamResource(new ByteArrayInputStream(res));

		return ResponseEntity.ok()
				.header("Content-Disposition", String.format(
						"attachment; filename=%s_export.xlsx", dto.getClass().getSimpleName().toLowerCase()))
				.contentLength(res.length)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
	
	/**
	 * Check for input validation
	 * 
	 * @param validator to use for validation
	 * @param dto Object to validate
	 * @param bindingResult to store validation errors
	 * @param validationData {@link Object} the validation data to pass within model and errors to validator
	 * @throws BadRequestException if validation not success
	 */
	default void validate(Validator validator, Object dto, BindingResult bindingResult, DataLoad validationData) throws CoreException {
		if (null != validator) {
			if (validator instanceof CrudValidator) {
				((CrudValidator) validator).setValidationData(validationData);
			}
			ValidationUtils.invokeValidator(validator, dto, bindingResult);
			if (bindingResult.hasErrors()) {
				throw new BadRequestException(bindingResult);
			}		
		}
	}
}