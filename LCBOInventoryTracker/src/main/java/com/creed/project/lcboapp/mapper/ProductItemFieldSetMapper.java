package com.creed.project.lcboapp.mapper;

import com.creed.project.lcboapp.domain.LCBOProduct;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Scope(value = "step")
public class ProductItemFieldSetMapper implements FieldSetMapper<LCBOProduct> {

    @Override
    public LCBOProduct mapFieldSet(final FieldSet fieldSet) {

        LCBOProduct entity = new LCBOProduct();

        /** The product file has multiple formats, so we check to see if the fieldset has certain amount of items.
         * If it's 41, it's the old version
         * Else it's the new version.
         */

        if (fieldSet.getFieldCount() == 41) {
            //Old version
            final int IDX_ID = 0;
            final int IDX_NAME = 2;
            final int IDX_PRICE_IN_CENTS = 5;
            final int IDX_REGULAR_PRICE_IN_CENTS = 6;
            final int IDX_PRIMARY_CATEGORY = 12;
            final int IDX_SECONDARY_CATEGORY = 13;
            final int IDX_ORIGIN = 14;
            final int IDX_PRODUCER_NAME = 28;
            final int IDX_RELEASED_ON = 29;
            final int IDX_UPDATED_AT = 40;

            entity.setId(fieldSet.readLong(IDX_ID));
            entity.setName(fieldSet.readString(IDX_NAME));
            entity.setPriceInCents(fieldSet.readDouble(IDX_PRICE_IN_CENTS));
            entity.setRegularPriceInCents(fieldSet.readDouble(IDX_REGULAR_PRICE_IN_CENTS));
            entity.setPrimaryCategory(fieldSet.readString(IDX_PRIMARY_CATEGORY));
            entity.setSecondaryCategory(fieldSet.readString(IDX_SECONDARY_CATEGORY));
            entity.setOrigin(fieldSet.readString(IDX_ORIGIN));
            entity.setProducerName(fieldSet.readString(IDX_PRODUCER_NAME));
            entity.setReleasedOn(parseDate(fieldSet.readRawString(IDX_RELEASED_ON)));
            entity.setUpdatedAt(parseDateTime(fieldSet.readRawString(IDX_UPDATED_AT)));
        }

        if (fieldSet.getFieldCount() == 46) {
            //New version
            final int IDX_ID = 0;
            final int IDX_NAME = 2;
            final int IDX_PRICE_IN_CENTS = 5;
            final int IDX_REGULAR_PRICE_IN_CENTS = 6;
            final int IDX_PRIMARY_CATEGORY = 12;
            final int IDX_SECONDARY_CATEGORY = 13;
            final int IDX_ORIGIN = 14;
            final int IDX_PRODUCER_NAME = 27;
            final int IDX_RELEASED_ON = 28;
            final int IDX_UPDATED_AT = 39;
            final int IDX_IMAGE_URL = 41;
            final int IDX_VARIETAL = 42;
            final int IDX_STYLE = 43;
            final int IDX_TERTIARY_CATEGORY = 44;

            entity.setId(fieldSet.readLong(IDX_ID));
            entity.setName(fieldSet.readString(IDX_NAME));
            entity.setPriceInCents(fieldSet.readDouble(IDX_PRICE_IN_CENTS));
            entity.setRegularPriceInCents(fieldSet.readDouble(IDX_REGULAR_PRICE_IN_CENTS));
            entity.setPrimaryCategory(fieldSet.readString(IDX_PRIMARY_CATEGORY));
            entity.setSecondaryCategory(fieldSet.readString(IDX_SECONDARY_CATEGORY));
            entity.setOrigin(fieldSet.readString(IDX_ORIGIN));
            entity.setProducerName(fieldSet.readString(IDX_PRODUCER_NAME));
            entity.setReleasedOn(parseDate(fieldSet.readRawString(IDX_RELEASED_ON)));
            entity.setUpdatedAt(parseDateTime(fieldSet.readRawString(IDX_UPDATED_AT)));
            entity.setImageUrl(fieldSet.readString(IDX_IMAGE_URL));
            entity.setVarietal(fieldSet.readString(IDX_VARIETAL));
            entity.setStyle(fieldSet.readString(IDX_STYLE));
            entity.setTertiaryCategory(fieldSet.readString(IDX_TERTIARY_CATEGORY));
        }

        return entity;
    }

    /**
     * This is created to handle the possibility that there's no value for the submitted time in the csv file.
     * If so, it returns the dummy date 0999-12-31 for all date values to validate correctly, as there's no chance
     * this will bne used
     *
     * @param localDateString
     * @return
     */
    private LocalDate parseDate(final String localDateString) {

        LocalDate localDateReturnString = LocalDate.parse("0999-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (!localDateString.isEmpty()) {
            localDateReturnString = LocalDate.parse(localDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        return localDateReturnString;
    }

    private LocalDateTime parseDateTime(final String localDateTimeString) {

        String[] localDateTimeParsed = localDateTimeString.split(Pattern.quote("."));

        return LocalDateTime.parse(localDateTimeParsed[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}