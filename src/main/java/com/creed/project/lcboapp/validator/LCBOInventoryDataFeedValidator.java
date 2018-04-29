package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.common.UtilClass;
import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import com.creed.project.lcboapp.exception.InvalidStoreFieldValue;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        validateUpdatedOn(entity.getUpdatedOn());
        validateUpdatedAt(entity.getUpdatedAt());
        validateCreatedAt(entity.getCreatedAt());
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

    private static void validateUpdatedOn(final LocalDate updatedOn) {
        if (UtilClass.validateDate(updatedOn)) { return; }
        throw new InvalidInventoryFieldValue("updatedOn", updatedOn);
    }

    private static void validateUpdatedAt(final LocalDateTime updatedAt) {
        if (UtilClass.validateDate(updatedAt)) { return; }
        throw new InvalidInventoryFieldValue("updatedAt", updatedAt);
    }

    private static void validateCreatedAt(final LocalDateTime createdAt) {
        if (UtilClass.validateDate(createdAt)) { return; }
        throw new InvalidInventoryFieldValue("createdAt", createdAt);
    }
}