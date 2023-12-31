/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.handler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.cursor.Cursor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.TableColumn;
import com.nttdata.core.crud.model.CrudDataLoad;
import com.nttdata.core.i18n.service.I18nService;

import lombok.extern.slf4j.Slf4j;

/**
 * A excel handler to convert the data to an excel workbook.
 * 
 * @param <T> Any object extending {@link Core}
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class ExcelExportableCursorHandler<T extends Core<?>> implements ExportableCursorHandler<T> {
	
	/** Optional service to translate i18n keys. */
	private final I18nService i18nService;
	
	/**
	 * Create a new object without i18n translate capabilities
	 */
	public ExcelExportableCursorHandler() {
		super();
		this.i18nService = null;
	}
	
	/**
	 * Create a new object with i18n translate capabilities
	 * @param service {@link I18nService} the i18n service. Must not be null.
	 */
	public ExcelExportableCursorHandler(I18nService service) {
		super();
		Assert.notNull(service, "I18nService must not be null.");
		this.i18nService = service;
	}
	
	@Override
	public InputStream export(Cursor<T> cursor, CrudDataLoad dataload) {
		InputStream is = null;
		try(Workbook workbook = new XSSFWorkbook()) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Sheet sheet = workbook.createSheet(translateIfPossible("i18n.messages.record.list"));
			cursor.forEach(data -> {
			   try {
				   writeRecord(cursor.getCurrentIndex(), dataload, sheet, data);
			   } catch (Exception e) {
				   log.error("Error durante la escritura del registro en el excel", e);
			   }
			});
			workbook.write(baos);
			is = new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray()));
		} catch (Exception e) {
			log.error("Error durante la escritura del excel", e);
		}
		return is;
	}

	private int writeRecord(int cursorIndex, CrudDataLoad dataload, 
			Sheet sheet, T data) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Row rowHeader = null;
		CellStyle headerStyle = null;
		if (cursorIndex == 0) {
			rowHeader = sheet.createRow(cursorIndex);
			headerStyle = createDefaultHeaderStyle(sheet.getWorkbook());
		}
		Row row = sheet.createRow(cursorIndex + 1);
		int columnIndex = 0;
		for (TableColumn column : dataload.getColumns()) {
			if (cursorIndex == 0) {
				createCell(rowHeader, columnIndex, translateIfPossible(column.getName()), headerStyle);
			}
			createCell(row, columnIndex, PropertyUtils.getNestedProperty(data, column.getProp()), null);
			columnIndex++;
		}
		return columnIndex;
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		Cell cell = row.createCell(columnCount);
		if (value instanceof Number) {
			cell.setCellValue(((Number) value).doubleValue());
		} else if (value instanceof Boolean) {
			String boolValueString = translateIfPossible("i18n.messages.yes.no." + ((Boolean)value).compareTo(true));
			cell.setCellValue(boolValueString);
		} else {
			cell.setCellValue((String) value);
		}
		// Para los tipos boolean usaremos el servicio de i18n.
		cell.setCellStyle(style);
	}
	
	protected String translateIfPossible(String i18n) {
		if (null != this.i18nService) {
			return this.i18nService.getTranslation(i18n);
		}
		return i18n;
	}
}
