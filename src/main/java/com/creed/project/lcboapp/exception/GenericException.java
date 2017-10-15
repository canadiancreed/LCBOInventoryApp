package com.creed.project.lcboapp.exception;

/**
 * Generic Exception Base Class
 *
 * todo: Is this class really needed, or is it over abstraction?
 */
public class GenericException extends RuntimeException {

    private final String errorCode;
    private final String errorLevel;
    private final String errorMessage;

    /**
     * Constructor
     *
     * @param errorMessage error message
     */
    public GenericException(String errorMessage) {
        super(errorMessage);
        this.errorCode = "";
        this.errorLevel = "";
        this.errorMessage = errorMessage;
    }

    /**
     * Constructor
     *
     * @param errorCode    error code
     * @param errorLevel   error level
     * @param errorMessage error message
     */
    public GenericException(String errorCode, String errorLevel, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorLevel = errorLevel;
        this.errorMessage = errorMessage;
    }

    /**
     * @return error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return error level
     */
    public String getErrorLevel() {
        return errorLevel;
    }

    /**
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
