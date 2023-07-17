/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.common.model;

import java.io.Serializable;

import com.nttdata.core.common.EnabledOnEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity to represent a table button in the front app
 * 
 * @see com.nttdata.core.common.service.Initializable#init(Page)
 * @see com.nttdata.core.common.service.Initializable#init(Core)
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Button implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The name of the button.
	 * @param name {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
	/** 
	 * The icon of the button.
	 * @param icon {@link String} The icon
	 * @return {@link String} the icon
	 */
	private String icon;
	
	/** 
	 * A function handler declared in the front page.
	 * @param handler {@link String} The handler function name
	 * @return {@link String} the handler function name
	 */
	private String handler;
	
	/** 
	 * Is the button enabled?. Default is true.
	 * @param enabled {@link EnabledOnEnum} The enabled state
	 * @return {@link EnabledOnEnum} the enabled state
	 */
	private EnabledOnEnum enabled;
	
	/** 
	 * A function handler declared in the front page
	 * that evaluates if the button should be enabled or not.
	 * @param enabledCondition {@link String} The handler function name
	 * @return {@link String} the handler function name
	 */
	private String enabledCondition;
	
	/** 
	 * The roles that allow the access to this button.
	 * @param roles {@link String}[] The roles
	 * @return {@link String}[] the roles
	 */
	private String[] roles;
	
	/** 
	 * Evaluate if the button is shown for each row or not.
	 * @param isRowButton {@link Boolean} The row button state
	 * @return {@link Boolean} the row button state
	 */
	private boolean isRowButton;
	
	/** 
	 * Evaluate if the button should be disabled when front validation fails.
	 * @param disabledWhenInvalid {@link Boolean} The disabled state
	 * @return {@link Boolean} the disabled state
	 */
    private boolean disabledWhenInvalid;

}
