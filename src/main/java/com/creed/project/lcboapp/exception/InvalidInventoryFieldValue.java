package com.creed.project.lcboapp.exception;

public class InvalidInventoryFieldValue extends GenericException {

    public InvalidInventoryFieldValue(final String fieldName, final Integer fieldValue) {
        super("48", "Fatal", "Invalid value found while processing inventory file. " +
                "FieldName: " + fieldName + " FieldValue: " + fieldValue + "  File cannot be processed.");
    }

    public InvalidInventoryFieldValue(final String fieldName, final String fieldValue) {
        super("48", "Fatal", "Invalid value found while processing inventory file. " +
                "FieldName: " + fieldName + " FieldValue: " + fieldValue + "  File cannot be processed.");
    }
}
