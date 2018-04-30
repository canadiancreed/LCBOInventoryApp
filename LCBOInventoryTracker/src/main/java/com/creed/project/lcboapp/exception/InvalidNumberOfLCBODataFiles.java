package com.creed.project.lcboapp.exception;

public class InvalidNumberOfLCBODataFiles extends GenericException {

    public InvalidNumberOfLCBODataFiles() {
        super("48", "Fatal", "Invalid amount of LCBO data files for this zip file. Aborting.");
    }
}
