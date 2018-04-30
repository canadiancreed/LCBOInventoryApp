package com.creed.project.lcboapp.validator;

import com.creed.project.lcboapp.common.UtilClass;
import com.creed.project.lcboapp.domain.LCBOStore;
import com.creed.project.lcboapp.exception.InvalidInventoryFieldValue;
import com.creed.project.lcboapp.exception.InvalidProductFieldValue;
import com.creed.project.lcboapp.exception.InvalidStoreFieldValue;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

public final class LCBOStoreDataFeedValidator {

    /**
     * Default Constructor
     */
    private LCBOStoreDataFeedValidator() {
        throw new IllegalAccessError("Store Data Feed Validation Policy Class");
    }

    public static void validateAllBizRules(final LCBOStore store) {

        validateId(store.getId());
        validateAddressLineOne(store.getAddressLineOne());
        validateAddressLineTwo(store.getAddressLineTwo());
        validateCity(store.getCity());
        validatePostalCode(store.getPostalCode());
        validateLatitude(store.getLatitude());
        validateLongitude(store.getLongitude());
        validateUpdatedAt(store.getUpdatedAt());
    }

    private static void validateId(final Long id) {
        if (StringUtils.isNumeric(id.toString())) { return; }
        throw new InvalidStoreFieldValue("id", id);
    }

    private static void validateAddressLineOne(final String addressLineOne) {
        if (StringUtils.isNotBlank(addressLineOne)) { return; }
        throw new InvalidStoreFieldValue("addressLineOne", addressLineOne);
    }

    //Can be blank
    private static void validateAddressLineTwo(final String addressLineTwo) {
        if (StringUtils.isBlank(addressLineTwo)) { return; }
        if (StringUtils.isAsciiPrintable(addressLineTwo)) { return; }
        throw new InvalidStoreFieldValue("addressLineTwo", addressLineTwo);
    }

    private static void validateCity(final String city) {
        if (StringUtils.isNotBlank(city)) { return; }
        throw new InvalidStoreFieldValue("city", city);
    }

    private static void validatePostalCode(final String postalCode) {
        if (postalCode.matches("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$")) { return; }
        throw new InvalidStoreFieldValue("postalCode", postalCode);
    }

    private static void validateLatitude(final String latitude) {
        if (latitude.matches("^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$")) {
            return;
        }

        throw new InvalidStoreFieldValue("latitude", latitude);
    }

    private static void validateLongitude(final String longitude) {
        if (longitude.matches("^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$")) {
            return;
        }

        throw new InvalidStoreFieldValue("longitude", longitude);
    }

    private static void validateUpdatedAt(final LocalDateTime updatedAt) {
        if (UtilClass.validateDate(updatedAt)) { return; }
        throw new InvalidStoreFieldValue("updatedAt", updatedAt);
    }
}