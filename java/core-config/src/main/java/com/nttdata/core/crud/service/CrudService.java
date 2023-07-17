/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.service;

import java.io.InputStream;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.exception.WrapperRuntimeException;
import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.common.service.Initializable;
import com.nttdata.core.crud.handler.ExportableCursorHandler;
import com.nttdata.core.crud.handler.ExcelExportableCursorHandler;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.crud.model.CrudDataLoad;

/**
 * Generic service interface that implements generic method for crud operations.<br>
 * 
 * All methods in this interfaces are marked as @{@link Transactional}
 * 
 * @param <T> Any object extending {@link Core}
 * @author NTT DATA
 * @since 0.0.1
 */
@Transactional
public interface CrudService<T extends Core<?>> extends Initializable<T> {
	
	/**
	 * Persist a new record of T 
	 * 
	 * @param dto the record to persist
	 */
	public default void insert(T dto) {
		this.getMapper().insert(dto);
	}
	
	/**
	 * Updates an existing record
	 * 
	 * @param dto the record to update
	 */
	public default void update(T dto) {
		this.getMapper().update(dto);
	}
	
	/**
	 * Delete an existing record
	 * 
	 * @param dto the record to delete
	 */
	public default void delete(T dto) {
		this.getMapper().delete(dto);
	}
	
	/**
	 * Find a record of T
	 * 
	 * @param dto the record to find
	 * @return T record
	 */
	public default T find(T dto) {
		return this.getMapper().find(dto);
	}
	
	/**
	 * Find all records of T
	 * 
	 * @param dto with search criteria
	 * @return {@link Page} of T that meets the search criteria
	 * @throws CoreException in case of error
	 */
	public default Page<T> search(Page<T> dto) throws CoreException {
		return this.getMapper().search(dto);
	}
	
	/**
	 * Export all records of T.<br>
	 * A default excel handler will be used as handler producing a xlsx inputstream.
	 * 
	 * @param dto {@link Page} of {@link T} with search criteria
	 * @return {@link InputStream} with the exported data in xlsx format
	 * @throws CoreException in case of error
	 */
	public default InputStream export(Page<T> dto) throws CoreException {
		return export(dto, new ExcelExportableCursorHandler<>());
	}
	
	/**
	 * Export all records of T using the requested {@link ExportableCursorHandler}
	 * for the current type T.
	 * 
	 * @param dto {@link Page} of {@link T} with search criteria
	 * @param handler {@link ExportableCursorHandler} the handler used to export the records.
	 * @return {@link InputStream} with the exported data
	 * @throws CoreException in case of error
	 */
	public default InputStream export(Page<T> dto, ExportableCursorHandler<T> handler) throws CoreException {
		return export(dto, handler, (CrudDataLoad) this.init(dto));
	}
	
	/**
	 * Export all records of T using the requested {@link ExportableCursorHandler}
	 * for the current type T.
	 * 
	 * @param dto {@link Page} of {@link T} with search criteria
	 * @param handler {@link ExportableCursorHandler} the handler used to export the records.
	 * 	 When null a Default Excel Handler is used to export the data.
	 * @param dataload {@link CrudDataLoad} with extra data to process the page.
	 * @return {@link InputStream} with the exported data
	 * @throws CoreException in case of error
	 */
	public default InputStream export(Page<T> dto, ExportableCursorHandler<T> handler, CrudDataLoad dataload) throws CoreException {
		try (Cursor<T> cursor = this.getMapper().export(dto)) {
			return handler.export(cursor, dataload);
		} catch (Exception e) {
			throw new WrapperRuntimeException(e);
		}
	}
	
	/**
	 * Get the mapper class associated with the service implementation.
	 * 
	 * @return {@link CrudMapper} of T inside the service
	 */
	CrudMapper<T> getMapper();
}
