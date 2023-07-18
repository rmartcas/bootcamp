/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.utils;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;

import com.nttdata.core.common.model.Core;

/**
 * Class util for batch operations
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class BatchUtils {
		
	/** Private constructor */
	private BatchUtils() {
	}

	/**
	 * Perform a batch operation using statementId and list of objects.
	 * 
	 * @param <E> any object that extends {@link Core}
	 * @param sqlSessionTemplate {@link SqlSessionTemplate} to use in batch operation
	 * @param statementId String the statement to execute
	 * @param list the list of objects to execute within statementId
	 */
	public static <E extends Core<?>> void execute(SqlSessionTemplate sqlSessionTemplate, String statementId, List<E> list) {
		MyBatisBatchItemWriter<E> writer = new MyBatisBatchItemWriter<>();
		writer.setSqlSessionTemplate(sqlSessionTemplate);
		writer.setAssertUpdates(false);
	    writer.setStatementId(statementId);
	    writer.write(list);
	}
}
