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
            System.out.println(dtpe.getMessage());
        }

        return validatedDate;
    }

    public static boolean validateDate(final LocalDateTime dateValue) {
        boolean validatedDate = false;

        String dateValueString = dateValue.toString();

        String newDateString = dateValueString.replace("T", " ");

        //todo for some reason it chops off seconds of the value is all zeros. This manualally reattachs the value
        if (newDateString.length() == 16) {
            newDateString = newDateString + ":00";
        }

        try {
            LocalDateTime.parse(newDateString, Constants.DATETIME_FORMAT);
            validatedDate = true;
        } catch (DateTimeParseException dtpe) {
            System.out.println(dtpe.getMessage());
        }

        return validatedDate;
    }
}
