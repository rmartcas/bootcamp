/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.crud.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.ibatis.cursor.Cursor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nttdata.core.common.model.Core;
import com.nttdata.core.common.model.TableColumn;
import com.nttdata.core.crud.model.CrudDataLoad;
import com.nttdata.core.i18n.service.I18nService;

class ExcelExportableCursorHandlerTest {
	
	@Mock
	private I18nService service;
	
	@Mock
	private Cursor<Core<?>> cursor;
	
	private CrudDataLoad dataload;
	
	@InjectMocks
	private ExcelExportableCursorHandler<Core<?>> handler;
	
	@BeforeEach
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.handler = new ExcelExportableCursorHandler<>();
		
		this.dataload = new CrudDataLoad() {
			/** serialVersionUID */
			private static final long serialVersionUID = 1L;
		};
		this.dataload.setColumns(new ArrayList<>());
		TableColumn column = new TableColumn();
		column.setName("i18n.column.name");
		column.setProp("id");
		this.dataload.getColumns().add(column);

		Iterator<Core<?>> mockIterator = Mockito.mock(Iterator.class);
		Mockito.doCallRealMethod().when(cursor).forEach(any(Consumer.class));
		when(cursor.iterator()).thenReturn(mockIterator);
		when(cursor.getCurrentIndex()).thenReturn(0).thenReturn(1);
		when(mockIterator.hasNext()).thenReturn(true, true, false);
		Core core = new Core() {

			/** serialVersionUID */
			private static final long serialVersionUID = 1L;
		};
		when(mockIterator.next()).thenReturn(core);
	}

	@Test
	void testExportWithoutI18nServiceReturnInputStream() {
		InputStream is = handler.export(cursor, dataload);
		Assert.assertNotNull(is);
	}
	
	@Test
	void testExportWithI18nServiceReturnInputStream() {
		Mockito.when(service.getTranslation(Mockito.anyString(), Mockito.any())).thenReturn("Sheetname");
		
		this.handler = new ExcelExportableCursorHandler<>(service);
		InputStream is = handler.export(cursor, dataload);
		Assert.assertNotNull(is);
	}
	
	@Test
	void testWhenNoSheetNameThenNoStreamIsReturned() {		
		this.handler = new ExcelExportableCursorHandler<>(service);
		InputStream is = handler.export(cursor, dataload);
		Assert.assertNull(is);
	}

	@Test
	void testExportWithoutDataLoadThrowsException() {
		InputStream is = handler.export(cursor, null);
		Assert.assertNotNull(is);
	}
}
