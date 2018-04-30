package com.creed.project.lcboapp.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum LCBOFileTypeModel {
    INVENTORY("INVENTORY", "inventories"),
    PRODUCT("PRODUCT", "products"),
    STORE("STORE", "stores");

    /**
     * Look up table
     */
    private static final Map<String, LCBOFileTypeModel> lookup = new ConcurrentHashMap<>();

    static {
        for (LCBOFileTypeModel item : LCBOFileTypeModel.values()) {
            lookup.put(item.getName(), item);
        }
    }

    private final String name;
    private final String fileName;

    /**
     * @param name lcbo data type
     * @param name lcbo file name
     */
    LCBOFileTypeModel(final String name, final String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    /**
     * @param name lcbo file type
     * @return LCBOFileType enum if type exist otherwise return null
     */
    public static LCBOFileTypeModel get(final String name) {
        if (StringUtils.isNotBlank(name)) {
            return lookup.get(name);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }


}
