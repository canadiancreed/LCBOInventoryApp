package com.creed.project.lcboapp.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvalidInventoryFieldValue extends GenericException {

    public InvalidInventoryFieldValue(final String fieldName, final Integer fieldValue) {
        super("48", "Fatal", "Invalid value found while processing inventory file. " +
                "FieldName: " + fieldName + " FieldValue: " + fieldValue + "  File cannot be processed.");
    }

    public InvalidInventoryFieldValue(final String fieldName, final String fieldValue) {
        super("48", "Fatal", "Invalid value found while processing inventory file. " +
                "FieldName: " + fieldName + " FieldValue: " + fieldValue + "  File cannot be processed.");
    }

    public InvalidInventoryFieldValue(final String fieldName, final LocalDate fieldValue) {
        super("48", "Fatal", "Invalid value found while processing inventory file. " +
                "FieldName: " + fieldName + " FieldValue: " + fieldValue + "  File cannot be processed.");
    }

    public InvalidInventoryFieldValue(final String fieldName, final LocalDateTime fieldValue) {
        super("48", "Fatal", "Invalid value found while processing inventory file. " +
                "FieldName: " + fieldName + " FieldValue: " + fieldValue + "  File cannot be processed.");
    }
}
