package com.creed.project.lcboapp.exception;

import java.util.Date;

public class InvalidProductFieldValue extends GenericException {

    private static final String FATAL_STRING = "Fatal";

    private static final String FIELDNAME_STRING = "FieldName";
    private static final String FIELDVALUE_STRING = "FieldValue";
    private static final String FILE_PROCESS_FAIL = " File cannot be processed. ";

    private static final String INVALID_VALUE_FOUND = "Invalid value found while processing product file. ";

    public InvalidProductFieldValue(final String fieldName, final String fieldValue) {
        super("48", FATAL_STRING, INVALID_VALUE_FOUND +
                FIELDNAME_STRING + ": " + fieldName + " " + FIELDVALUE_STRING + ": " + fieldValue + " " +
                FILE_PROCESS_FAIL);
    }

    public InvalidProductFieldValue(final String fieldName, final Integer fieldValue) {
        super("48", FATAL_STRING, INVALID_VALUE_FOUND +
                FIELDNAME_STRING + ": " + fieldName + " " + FIELDVALUE_STRING + ": " + fieldValue + " " +
                FILE_PROCESS_FAIL);
    }

    public InvalidProductFieldValue(final String fieldName, final Long fieldValue) {
        super("48", FATAL_STRING, INVALID_VALUE_FOUND +
                FIELDNAME_STRING + ": " + fieldName + " " + FIELDVALUE_STRING + ": " + fieldValue + " " +
                FILE_PROCESS_FAIL);
    }

    public InvalidProductFieldValue(final String fieldName, final Double fieldValue) {
        super("48", FATAL_STRING, INVALID_VALUE_FOUND +
                FIELDNAME_STRING + ": " + fieldName + " " + FIELDVALUE_STRING + ": " + fieldValue + " " +
                FILE_PROCESS_FAIL);
    }

    public InvalidProductFieldValue(final String fieldName, final Date fieldValue) {
        super("48", FATAL_STRING, INVALID_VALUE_FOUND +
                FIELDNAME_STRING + ": " + fieldName + " " + FIELDVALUE_STRING + ": " + fieldValue + " " +
                FILE_PROCESS_FAIL);
    }
}