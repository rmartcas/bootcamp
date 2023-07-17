/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * Combo joins for the main table to filter
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ComboJoinTable implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public enum JoinType {
		INNER_JOIN("INNER JOIN"),
		LEFT_JOIN("LEFT JOIN"),
		RIGHT_JOIN("RIGHT JOIN"),
		FULL_JOIN("FULL JOIN");
		
		@Getter
		private final String type;
		
		private JoinType(String joinType) {
			this.type = joinType;
		}
	}
	
	/** 
	 * The table field used to filter
	 * @param table {@link String} The table to join
	 * @return {@link String} the table to join
	 */
	@NonNull
	private String table;
	
	/** 
	 * The table used to join within {@code table} field.
	 * In case to be null {@link ComboPage#table} field will be used.
	 * @param originTable {@link String} The table to join
	 * @return {@link String} the table to join
	 */
	private String originTable;
	
	/** 
	 * The join type to apply in this join
	 * @param type {@link JoinType} The join type
	 * @return {@link JoinType} the join type
	 */
	@NonNull
	private JoinType type;
	
	/** 
	 * The "from" table column to join with.
	 * @param originTableJoinColumn {@link String} The from column
	 * @return {@link String} the from column
	 */
	@NonNull
	private String originTableJoinColumn;
	
	/** 
	 * The "join" table column to join with.
	 * @param joinTableJoinColumn {@link String} The join column
	 * @return {@link String} the join column
	 */
	@NonNull
	private String joinTableJoinColumn;
	
	/**
	 * Constructor with optional {@code originTable}
	 * @param table {@link String} The table to join
	 * @param type {@link JoinType} The join type
	 * @param originTableJoinColumn {@link String} The from column
	 * @param joinTableJoinColumn {@link String} The join column
	 */
	public ComboJoinTable(String table, JoinType type, String originTableJoinColumn, String joinTableJoinColumn) {
		this(table, null, type, originTableJoinColumn, joinTableJoinColumn);
	}
	
	
}
