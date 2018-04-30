package com.creed.project.lcboapp.exception;

public class MissingLCBODataFileColumns extends GenericException {

    public MissingLCBODataFileColumns() {
        super("48", "Fatal", "There's no columns in this file. File cannot be processed.");
    }
}