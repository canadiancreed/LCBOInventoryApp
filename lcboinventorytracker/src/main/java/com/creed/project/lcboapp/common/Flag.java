package com.creed.project.lcboapp.common;

public enum Flag {
    YES("Y"),
    NO("N"),
    PROCESSING("P"),
    ERROR("E");

    private final String value;

    /**
     * @param value the flag value
     */
    Flag(String value) {
        this.value = value;
    }

    /**
     * @return the flag value
     */
    public String getValue() {
        return value;
    }
}
