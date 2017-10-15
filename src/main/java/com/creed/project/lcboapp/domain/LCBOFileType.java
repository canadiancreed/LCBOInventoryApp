package com.creed.project.lcboapp.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum LCBOFileType {
    INVENTORY("INVENTORY"), PRODUCT("PRODUCT"), STORE("STORE");

    /**
     * Look up table
     */
    private static final Map<String, LCBOFileType> lookup = new ConcurrentHashMap<>();

    static {
        for (LCBOFileType item : LCBOFileType.values()) {
            lookup.put(item.getType(), item);
        }
    }

    private final String type;

    /**
     * @param type lcbo file type
     */
    LCBOFileType(String type) {
        this.type = type;
    }

    /**
     * @param type lcbo file type
     * @return LCBOFileType enum if type exist otherwise return null
     */
    public static LCBOFileType get(String type) {
        if (StringUtils.isNotBlank(type)) {
            return lookup.get(type);
        }
        return null;
    }

    /**
     * @return lcbo file type
     */
    public String getType() {
        return type;
    }

}
