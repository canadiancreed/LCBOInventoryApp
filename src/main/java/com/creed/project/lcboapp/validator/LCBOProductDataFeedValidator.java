package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.exception.InvalidProductFieldValue;
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
        if (StringUtils.isNumeric(id.toString())) { return; }
        throw new InvalidProductFieldValue("id", id);
    }

    private static void validateName(final String name){
        if (StringUtils.isNotBlank(name)) { return; }
        throw new InvalidProductFieldValue("name", name);
    }

    private static void validatePriceInCents(final Double priceInCents){
        if (StringUtils.isNotBlank(priceInCents.toString())) { return; }
        throw new InvalidProductFieldValue("priceInCents", priceInCents);
    }

    private static void validateRegularPriceInCents(final Double regularPriceInCents){
        if (StringUtils.isNotBlank(regularPriceInCents.toString())) { return; }
        throw new InvalidProductFieldValue("regularPriceInCents", regularPriceInCents);
    }

    private static void validatePrimaryCategory(final String primaryCategory){
        if (StringUtils.isBlank(primaryCategory)) { return; }

        if (StringUtils.isAlphanumericSpace(primaryCategory)) { return; }
        throw new InvalidProductFieldValue("primaryCategory", primaryCategory);
    }

    private static void validateSecondaryCategory(final String secondaryCategory){
//        if (StringUtils.isBlank(secondaryCategory)) { return; }
//
//        if (StringUtils.isAlphanumericSpace(secondaryCategory)) { return; }
//        throw new InvalidProductFieldValue("secondaryCategory", secondaryCategory);
    }

    private static void validateOrigin(final String origin){
//        if (StringUtils.isBlank(origin)) { return; }
//
//        if (StringUtils.isAlphanumericSpace(origin)) { return; }
//        throw new InvalidProductFieldValue("origin", origin);
    }

    private static void validateProducerName(final String producerName){
//        if (StringUtils.isBlank(producerName)) { return; }
//
//        if (StringUtils.isAlphanumericSpace(producerName)) { return; }
//        throw new InvalidProductFieldValue("producerName", producerName);
    }

    private static void validateReleasedOn(final String releasedOn){
        if (StringUtils.isBlank(releasedOn)) { return; }

        if (StringUtils.isNotBlank(releasedOn)) { return; }
        throw new InvalidProductFieldValue("releasedOn", releasedOn);
    }

    private static void validateUpdatedAt(final Date updatedAt){
        if (StringUtils.isNotBlank(updatedAt.toString())) { return; }
        throw new InvalidProductFieldValue("updatedAt", updatedAt);
    }

    //Optional

    private static void validateImageUrl(final String imageUrl){
        if (StringUtils.isBlank(imageUrl)) { return; }

        if (StringUtils.isAlphanumericSpace(imageUrl)) { return; }
        throw new InvalidProductFieldValue("imageUrl", imageUrl);
    }

    private static void validateVarietal(final String varietal){
        if (StringUtils.isBlank(varietal)) { return; }

        if (StringUtils.isAlphanumericSpace(varietal)) { return; }
        throw new InvalidProductFieldValue("varietal", varietal);
    }

    private static void validateStyle(final String style){
        if (StringUtils.isBlank(style)) { return; }

        if (StringUtils.isAlphanumericSpace(style)) { return; }
        throw new InvalidProductFieldValue("style", style);
    }

    private static void validateTertiaryCategory(final String tertiaryCategory){
        if (StringUtils.isBlank(tertiaryCategory)) { return; }

        if (StringUtils.isAlphanumericSpace(tertiaryCategory)) { return; }
        throw new InvalidProductFieldValue("tertiaryCategory", tertiaryCategory);
    }
}