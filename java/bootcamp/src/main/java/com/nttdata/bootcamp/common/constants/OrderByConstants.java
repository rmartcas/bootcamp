/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.bootcamp.common.constants;

import com.nttdata.core.audit.model.AuditPage;
import com.nttdata.core.common.utils.OrderByConfig;
import com.nttdata.core.common.model.PageOrder;
import com.nttdata.core.profiles.model.ProfilePage;
import com.nttdata.core.users.model.UserPage;

/**
 * Class to define the allowed properties used to order queries.<br>
 * 
 * If a class ProfilePage has a "filters.id" property you can define that this property is mapped to "profile_id" column.
 * 
 * @author NTT DATA
 * @since 0.0.1
 *
 */
public final class OrderByConstants {
	
	private OrderByConstants() {
	}

    public static void init() {
        // Just for initialize the static block
    }

    static {
    	OrderByConfig.addOrderBy(ProfilePage.class, "filters.id", new PageOrder("profile_id"));
    	OrderByConfig.addOrderBy(UserPage.class, "filters.profile", new PageOrder("profile_id"));
    	OrderByConfig.addOrderBy(AuditPage.class, "filters.requestId", new PageOrder("request_id"));
    	OrderByConfig.addOrderBy(AuditPage.class, "filters.action", new PageOrder("audit_action"));
    	OrderByConfig.addOrderBy(AuditPage.class, "filters.pairKey", new PageOrder("pair_key"));
    	OrderByConfig.addOrderBy(AuditPage.class, "filters.user", new PageOrder("audit_user"));
    	OrderByConfig.addOrderBy(AuditPage.class, "filters.created", new PageOrder("audit_date"));
    }
}
