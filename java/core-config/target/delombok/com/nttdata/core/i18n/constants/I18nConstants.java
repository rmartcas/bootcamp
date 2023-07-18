/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.i18n.constants;

/**
 * I18n constants for i18n module
 */
public final class I18nConstants {
	
	/** I18n prefix */
	public static final String I18N_PREFIX = "i18n.";
	
	/** I18n error prefix used in global error handler */
	public static final String I18N_ERROR_PREFIX = I18N_PREFIX + "error.";
	
	/** I18n validation prefix used in validation */
	public static final String I18N_VALIDATION_PREFIX = I18N_PREFIX + "validation.";
	
	/** i18n.error.400 */
	public static final String I18N_VALIDATION_INVALID_DATA = I18N_ERROR_PREFIX + "400";
	
	/** i18n.validation.field.duplicated */
	public static final String I18N_VALIDATION_FIELD_DUPLICATED = I18N_VALIDATION_PREFIX + "field.duplicated";
	
	/** i18n.validation.file.size.upload.exceeded */
	public static final String I18N_VALIDATION_FILE_MAX_SIZE_UPLOAD_EXCEEDED = I18N_VALIDATION_PREFIX + "file.size.upload.exceeded";
	
	/** i18n.validation.field.invalid */
	public static final String I18N_VALIDATION_FIELD_INVALID = I18N_VALIDATION_PREFIX + "field.invalid";
	
	/** i18n.validation.field.required */
	public static final String I18N_VALIDATION_FIELD_REQUIRED = I18N_VALIDATION_PREFIX + "field.required";
	
	/** i18n.validation.field.maxlength */
	public static final String I18N_VALIDATION_FIELD_MAXLENGTH = I18N_VALIDATION_PREFIX + "field.maxlength";
	
    /** i18n.validation.record.in.use */
    public static final String I18N_VALIDATION_RECORD_IN_USE = I18N_VALIDATION_PREFIX + "record.in.use";


	/**
	 * Default constructor<br>
	 */
	private I18nConstants() {
	}
}