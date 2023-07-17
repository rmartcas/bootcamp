/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.nttdata.core.common.model.PageOrder;

/**
 * Class to define the allowed properties used to order queries.<br>
 * 
 * If a class ProfilePage has a "filters.id" property you can define that this
 * property is mapped to "profile_id" column.
 * 
 * @author NTT DATA
 * @since 0.0.1
 *
 */
public final class OrderByConfig {

	private Map<Class<?>, Map<String, PageOrder>> orderBy = null;

	private static OrderByConfig instance = null;

	private OrderByConfig() {
		orderBy = new HashMap<>();
	}

	public static OrderByConfig getInstance() {
		if (instance == null) {
			instance = new OrderByConfig();
		}

		return instance;
	}

	public static Map<String, PageOrder> getOrderBy(Class<?> clazz) {
		return getInstance().orderBy.get(clazz);
	}

	public static void addOrderBy(Class<?> clazz, String field, PageOrder order) {
		if (null == getOrderBy(clazz)) {
			getInstance().orderBy.put(clazz, new HashMap<>());
		}
		getInstance().orderBy.get(clazz).put(field, order);
	}
}
