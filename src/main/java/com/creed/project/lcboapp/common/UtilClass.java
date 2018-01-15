package com.creed.project.lcboapp.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class UtilClass {

    private UtilClass() {
        //do nothing
    }

    public static boolean validateDate(final LocalDate dateValue) {
        boolean validatedDate = false;

        try {
            LocalDate.parse(dateValue.toString(), Constants.DATE_FORMAT);
            validatedDate = true;
        } catch (DateTimeParseException dtpe) {
            //No action needed.
        }

        return validatedDate;
    }

    public static boolean validateDate(final LocalDateTime dateValue) {
        boolean validatedDate = false;

        try {
            LocalDateTime.parse(dateValue.toString(), Constants.DATETIME_FORMAT);
            validatedDate = true;
        } catch (DateTimeParseException dtpe) {
            //No action needed.
        }

        return validatedDate;
    }
}
