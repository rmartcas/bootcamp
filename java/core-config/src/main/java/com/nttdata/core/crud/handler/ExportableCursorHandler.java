/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.handler;

import java.io.InputStream;
import org.apache.ibatis.cursor.Cursor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.crud.model.CrudDataLoad;

/**
 * Generic handler interface used to convert a {@link Cursor} of T
 * to a {@link InputStream}.
 *
 * @see ExcelExportableCursorHandler
 * @param <T> Any object extending {@link Core}
 * @author NTT DATA
 * @since 0.0.1
 */
public interface ExportableCursorHandler<T extends Core<?>> {

	/**
	 * Export the data in <code>cursor</code>.<br>
	 * To support export process a {@link CrudDataLoad} should be passed.<br>
	 * 
	 * @param cursor {@link Cursor} of {@link T} the cursor to export
	 * @param dataload {@link CrudDataLoad} used to support the export process.
	 * @return {@link InputStream} the resulting exported data file
	 */
	InputStream export(Cursor<T> cursor, CrudDataLoad dataload);
	
	/**
	 * Create a default header style to apply to header columns
	 *
	 * @param workbook {@link Workbook} the workbook used
	 * @return {@link CellStyle} the default header style
	 */
	default CellStyle createDefaultHeaderStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		font.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		return style;
	}
}
