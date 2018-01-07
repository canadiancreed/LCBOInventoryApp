package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public final class LCBOInventoryDataFeedValidator {

    /**
     * Default Constructor
     */
    private LCBOInventoryDataFeedValidator() {
        throw new IllegalAccessError("Inventory Data Feed Validation Policy Class");
    }

    public static void validateAllBizRules(final LCBOInventory entity) {
        validateProductID(entity.getProductID());
        validateStoreID(entity.getStoreID());
        validateQuantity(entity.getQuantity());
        validateUpdatedAt(entity.getUpdatedAt());
        validateCreatedAt(entity.getCreatedAt());
        validateUpdatedOn(entity.getUpdatedOn());
    }

    private static void validateProductID(final Integer productID) {
        if (StringUtils.isNumeric(productID.toString())) { return; }
        throw new InvalidInventoryFieldValue("productID", productID);
    }

    private static void validateStoreID(final Integer storeID) {
        if (StringUtils.isNumeric(storeID.toString())) { return; }
        throw new InvalidInventoryFieldValue("storeID", storeID);
    }

    private static void validateQuantity(final Integer quantity) {
        if (StringUtils.isNumeric(quantity.toString())) { return; }
        throw new InvalidInventoryFieldValue("quantity", quantity);
    }

    private static void validateUpdatedAt(final Date updatedAt) {
        //Need check for Dates
    }

    private static void validateCreatedAt(final Date createdAt) {
        //Need check for Dates
    }

    private static void validateUpdatedOn(final Date updatedOn) {
        //Need check for Dates
    }
}