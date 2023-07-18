/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.interceptor;

import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.common.utils.PaginationUtils;
import com.nttdata.core.context.ApplicationContextHolder;
import com.nttdata.core.context.ConfigProperties;

/**
 * Interceptor for pagination settings before searchs and pagination results after search
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Intercepts({
	  @Signature(type = Executor.class, method = CommonConstants.QUERY, 
			  args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
	  @Signature(type = Executor.class, method = CommonConstants.QUERY, 
	  args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class })
	})
public class PaginationInterceptor implements Interceptor {
	
    /** mapped statement parameter index. */
    private static final int MAPPED_STATEMENT_INDEX = 0;
    
    /** parameter index. */
    private static final int PARAMETER_INDEX = 1;
	
    /** Configuration properties */
    private ConfigProperties configProperties;

	/**
	 * Prepare the pagination object before search and after search.
	 * 
	 * @param invocation Invocation with interceptor data
	 * @return Object intercepted method result
	 * @throws Throwable if any error
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		final Object parameter = args[PARAMETER_INDEX];
	    Object result = null;
	     
	    if (parameter instanceof Page) {
	    	if (null == configProperties) {
	    		this.configProperties = ApplicationContextHolder.getApplicationContext().getBean(ConfigProperties.class);
	    	}

	    	final MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];

	    	Page<?> searchCriteriaDto = handlePrePagination(args, parameter, ms);
	    	result = invocation.proceed();
	    	handlePostPagination(result, searchCriteriaDto);
	    } else {
	    	result = invocation.proceed();
	    }
	    return result;
	}

	/**
	 * Setup paged query for search
	 *
	 * @param args Invocation args
	 * @param parameter The search parameter must be an instance of {@link Page}
	 * @param ms {@link MappedStatement}
	 * @param searchCriteriaDto
	 */
	private Page<?> handlePrePagination(Object[] args, final Object parameter, final MappedStatement ms) {
		Page<?> searchCriteriaDto = (Page<?>) parameter;
		PaginationUtils.preparePagination(configProperties, searchCriteriaDto);
		 
		BoundSql boundSql = ms.getBoundSql(parameter);
		String originalSql = boundSql.getSql();
		String pagedSql = searchCriteriaDto.getPrefix() + originalSql + searchCriteriaDto.getSuffix();

		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), pagedSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSource(newBoundSql));
		args[MAPPED_STATEMENT_INDEX] = newMs;
		
		return searchCriteriaDto;
	}
	
	@SuppressWarnings(CommonConstants.UNCHECKED)
	private void handlePostPagination(Object result, Page<?> searchCriteriaDto) {
		if (result instanceof List) {
			 List<Page<?>> resultList = (List<Page<?>>) result;
			 
			 Page<?> resultDto = getResultDto(searchCriteriaDto, resultList);
			
			resultDto.setCurrentPage(searchCriteriaDto.getCurrentPage());
			resultDto.setSize(searchCriteriaDto.getSize());
			PaginationUtils.preparePaginationResults(resultDto);
		 }
	}

	private Page<?> getResultDto(Page<?> searchCriteriaDto, List<Page<?>> resultList) {
		Page<?> resultDto = null;
		
		if (!resultList.isEmpty()) {
			resultDto = resultList.get(0);
		}
		
		if (null == resultDto) {
			 resultDto = BeanUtils.instantiateClass(searchCriteriaDto.getClass());
			 resultList.add(resultDto);
		}
		return resultDto;
	}
	
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
	        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
	        builder.resource(ms.getResource());
	        builder.fetchSize(ms.getFetchSize());
	        builder.statementType(ms.getStatementType());
	        builder.keyGenerator(ms.getKeyGenerator());
	        String[] keyProperties = ms.getKeyProperties();
	        builder.keyProperty(keyProperties == null ? null : keyProperties[0]);
	        //setStatementTimeout()
	        builder.timeout(ms.getTimeout());
	        //setStatementResultMap()
	        builder.parameterMap(ms.getParameterMap());
	        //setStatementResultMap()
	        builder.resultMaps(ms.getResultMaps());
	        builder.resultSetType(ms.getResultSetType());
	        //setStatementCache()
	        builder.cache(ms.getCache());
	        builder.flushCacheRequired(ms.isFlushCacheRequired());
	        builder.useCache(ms.isUseCache());
	        return builder.build();
	    }

	private class BoundSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}
