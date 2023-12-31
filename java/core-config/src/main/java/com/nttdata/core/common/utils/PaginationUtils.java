/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.utils;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.common.model.PageOrder;
import com.nttdata.core.context.ConfigProperties;

/**
 * Class util for page calculation
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class PaginationUtils {
	
	/** Private constructor */
	private PaginationUtils() {

	}
	
	/**
	 * Prepare pagination settings for sql filter
	 *
	 * @param configProperties {@link ConfigProperties} the application config properties
	 * @param search {@link Page} to prepare
	 */
	public static void preparePagination(ConfigProperties configProperties, Page<?> search) {		
		if (search.getCurrentPage() <= 0) {
			search.setCurrentPage(configProperties.getBbdd().getPagination().getPage());
		}
		
		if (search.getSize() == 0) {
			search.setSize(configProperties.getBbdd().getPagination().getSize());
		}

		StringBuilder prefijo = new StringBuilder()
				.append(StringUtils.defaultString(search.getPrefix(), configProperties.getBbdd().getPagination().getPrefix()));
		StringBuilder sufijo = new StringBuilder()
				.append(StringUtils.defaultString(search.getSuffix(), configProperties.getBbdd().getPagination().getSuffix()));
		prepareOrder(search, sufijo);
		if (-1 != search.getSize()) {
			getPaginationLimits(configProperties, search, sufijo);
		}
		search.setPrefix(prefijo.toString());
		search.setSuffix(sufijo.toString());
	}
	
	/**
	 * Prepare sort settings for sql filters.
	 * 
	 * Only valid properties will be added to order by clause
	 *
	 * @param search {@link Page} to prepare
	 * @param sufijo {@link StringBuilder} with pagination query
	 */
    protected static void prepareOrder(Page<?> search, StringBuilder sufijo) {
        if (null == search.getPageOrder() || search.getPageOrder().isEmpty()) {
            return;
        }
        List<String> sb = new ArrayList<>();
        for (PageOrder pageOrder : search.getPageOrder()) {
            if (validatePageOrder(search, pageOrder)) {
            	addPageOrder(sb, search, pageOrder);
            }
        }

        String orderBy = StringUtils.join(sb, CommonConstants.COMMA);
        if (orderBy.length() > 0) {
            sufijo.append(CommonConstants.ORDER_BY + CommonConstants.WHITESPACE);
            sufijo.append(orderBy + CommonConstants.WHITESPACE);
        }
    }
    
    /**
	 * Check if the requested property is a valid property inside <code>search</code> object.
	 * <br>
	 * The property is valid when it can be accessed across nested path, although its value can be null.
	 * <br>
	 * If sort property is not a valid field in <code>search</code>, it parent or nested objects it will return false.
	 *
	 * @param search {@link Page} to prepare
	 * @param pageOrder {@link PageOrder} with property to validate
	 */
    private static boolean validatePageOrder(Page<?> search, PageOrder pageOrder) {
		try {
			return PropertyUtils.isReadable(search, pageOrder.getProperty());
		} catch (Exception e) {
			log.info("Requested sort property cannot be accessed.", e);
			return false;
		}
	}
    
    /**
	 * Add the property to order by clause.
	 * 
	 * If a redefined property exists in {@link OrderByConfig} for <code>search</code> class and
	 * 
	 * {@link PageOrder#getProperty()} then it will be override the current property by this one
	 * 
	 * @param sb {@link List} of order by fragments
	 * @param search {@link Page} to prepare
	 * @param pageOrder {@link PageOrder} with property to add
	 */
	private static void addPageOrder(List<String> sb, Page<?> search, PageOrder pageOrder) {
		String sortProperty = pageOrder.getProperty();
			
		Map<String, PageOrder> sortedProperties = OrderByConfig.getOrderBy(search.getClass());
		if (sortedProperties != null) {
			PageOrder newOrder = sortedProperties.get(sortProperty);
			if (null != newOrder) {
				sortProperty = StringUtils.defaultIfBlank(newOrder.getProperty(), pageOrder.getProperty());
				if (newOrder.getSortAsString().booleanValue()) {
					sortProperty = " UPPER(" + Normalizer.normalize(sortProperty, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "") + ") ";
				}				
			}
		}
		sb.add(sortProperty + CommonConstants.WHITESPACE + pageOrder.getOrder());
	}
	
	private static void getPaginationLimits(ConfigProperties configProperties, Page<?> dto, StringBuilder sufijo) {
		MessageFormat msg = new MessageFormat(configProperties.getBbdd().getPagination().getLimits());
		
		// Format the pagination limits to avoid locale specific thousand separator
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		
		// No hay problemas de SQL injection aqui pues los datos de registro inicial/final son calculados
		final int registroInicial = (dto.getCurrentPage() - 1) * dto.getSize();
		sufijo.append(msg.format(new Object[] {
			nf.format(dto.getSize()),
			nf.format(registroInicial)
		}));
	}
	
	private static void prepareTotalPages(Page<?> dto) {
		if ((dto.getTotalRecords() == 0)) {
			dto.setTotalPages(0);
			return;
		}
		
		if (dto.getSize() == -1) {
			dto.setTotalPages(1);
			return;
		}
		
		int totalPaginas = (dto.getTotalRecords() - 1) / dto.getSize() + 1;
		dto.setTotalPages(totalPaginas);
	}
	
	/**
	 * Prepare pagination results after sql filter
	 *
	 * @param dto {@link Page} to prepare
	 */
	public static void preparePaginationResults(Page<?> dto) {
		if (CollectionUtils.isEmpty(dto.getRecords())) {
			dto.setTotalRecords(0);
		} else if (dto.getSize() == -1) {
			dto.setTotalRecords(dto.getRecords().size());
		}
		
		prepareTotalPages(dto);
	}
}
