package com.creed.project.lcboapp.exception;

public class MissingAPIData extends RuntimeException {

    public MissingAPIData() {
        super("No data was found in the submitted data file.");
    }
}
