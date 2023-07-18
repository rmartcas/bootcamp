/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.mapper;

import org.apache.ibatis.cursor.Cursor;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.Page;

/**
 * Generic interface implementing generic method for crud operations.
 * 
 * @param <T> Any object extending {@link Core}
 * @author NTT DATA
 * @since 0.0.1
 */
public interface CrudMapper<T extends Core<?>> {

	/**
	 * Persist a new record of T 
	 * 
	 * @param dto the record to persist
	 */
	void insert(T dto);
	
	/**
	 * Updates an existing record
	 * 
	 * @param dto the record to update
	 */
	void update(T dto);
	
	/**
	 * Delete an existing record
	 * 
	 * @param dto the record to delete
	 */
	void delete(T dto);
	
	/**
	 * Find a record of T
	 * 
	 * @param dto the record to find
	 * @return T record
	 */
	T find(T dto);
	
	/**
	 * Find all records of T
	 * 
	 * @param dto with search criteria
	 * @return {@link Page} of T that meets the search criteria
	 */
	Page<T> search(Page<T> dto);
	
	/**
	 * Export all records of T
	 * 
	 * @param dto with search criteria
	 * @return {@link Cursor} of {@link T} records that meets the search criteria
	 */
	Cursor<T> export(Page<T> dto);
}
