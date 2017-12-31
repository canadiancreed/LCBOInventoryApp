package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public final class LCBOProductDataFeedValidator {

    /**
     * Default Constructor
     */
    private LCBOProductDataFeedValidator() {
        throw new IllegalAccessError("Product Data Feed Validation Policy Class");
    }

    public static void validateAllBizRules ( final LCBOProduct entity){
        validateID(entity.getId());
        validateName(entity.getName());
        validatePriceInCents(entity.getPriceInCents());
        validateRegularPriceInCents(entity.getRegularPriceInCents());
        validatePrimaryCategory(entity.getPrimaryCategory());
        validateSecondaryCategory(entity.getSecondaryCategory());
        validateOrigin(entity.getOrigin());
        validateProducerName(entity.getProducerName());
        validateReleasedOn(entity.getReleasedOn());
        validateUpdatedAt(entity.getUpdatedAt());
        validateImageUrl(entity.getImageOrl());
        validateVarietal(entity.getVarietal());
        validateStyle(entity.getStyle());
        validateTertiaryCategory(entity.getTertiaryCategory());
    }

    private static void validateID(final Long id){

    }

    private static void validateName(final String id){

    }

    private static void validatePriceInCents(final Double id){

    }

    private static void validateRegularPriceInCents(final Double id){

    }

    private static void validatePrimaryCategory(final String id){

    }

    private static void validateOrigin(final String id){

    }

    private static void validateProducerName(final String id){

    }

    private static void validateReleasedOn(final Date id){

    }

    private static void validateSecondaryCategory(final String id){

    }

    private static void validateUpdatedAt(final Date updatedAt){
    }

    //Optional

    private static void validateImageUrl(final String imageUrl){
    }

    private static void validateVarietal(final String varietal){
    }

    private static void validateStyle(final String style){
    }

    private static void validateTertiaryCategory(final String tertiaryCategory){
    }




    private static void validateProductID ( final Integer productID){
        if (StringUtils.isNumeric(productID.toString())) {
            return;
        }
        throw new InvalidInventoryFieldValue("productID", productID);
    }

    private static void validateStoreID ( final Integer storeID){
        if (StringUtils.isNumeric(storeID.toString())) {
            return;
        }
        throw new InvalidInventoryFieldValue("storeID", storeID);
    }

    private static void validateQuantity ( final Integer quantity){
        if (StringUtils.isNumeric(quantity.toString())) {
            return;
        }
        throw new InvalidInventoryFieldValue("quantity", quantity);
    }
}