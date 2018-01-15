package com.creed.project.lcboapp.mapper;

import com.creed.project.lcboapp.domain.LCBOProduct;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Scope(value = "step")
public class ProductItemFieldSetMapper implements FieldSetMapper<LCBOProduct> {

    //Old fields
    private static final int IDX_ID = 0;
    private static final int IDX_NAME = 2;
    private static final int IDX_PRICE_IN_CENTS = 5;
    private static final int IDX_REGULAR_PRICE_IN_CENTS = 6;
    private static final int IDX_PRIMARY_CATEGORY = 12;
    private static final int IDX_SECONDARY_CATEGORY = 13;
    private static final int IDX_ORIGIN = 14;
    private static final int IDX_PRODUCER_NAME = 28;
    private static final int IDX_RELEASED_ON = 29;
    private static final int IDX_UPDATED_AT = 40;

    //New fields
//    private static final int IDX_ID = 0;
//    private static final int IDX_NAME = 2;
//    private static final int IDX_PRICE_IN_CENTS = 5;
//    private static final int IDX_REGULAR_PRICE_IN_CENTS = 6;
//    private static final int IDX_PRIMARY_CATEGORY = 12;
//    private static final int IDX_SECONDARY_CATEGORY = 13;
//    private static final int IDX_ORIGIN = 14;
//    private static final int IDX_PRODUCER_NAME = 27;
//    private static final int IDX_RELEASED_ON = 28;
//    private static final int IDX_UPDATED_AT = 38;
//    private static final int IDX_IMAGE_URL = 40;
//    private static final int IDX_VARIETAL = 41;
//    private static final int IDX_STYLE = 42;
//    private static final int IDX_TERTIARY_CATEGORY = 43;

    @Override
    public LCBOProduct mapFieldSet(final FieldSet fieldSet) {

        LCBOProduct entity = new LCBOProduct();

        entity.setId(fieldSet.readLong(IDX_ID));
        entity.setName(fieldSet.readString(IDX_NAME));
        entity.setPriceInCents(fieldSet.readDouble(IDX_PRICE_IN_CENTS));
        entity.setRegularPriceInCents(fieldSet.readDouble(IDX_REGULAR_PRICE_IN_CENTS));
        entity.setPrimaryCategory(fieldSet.readString(IDX_PRIMARY_CATEGORY));
        entity.setSecondaryCategory(fieldSet.readString(IDX_SECONDARY_CATEGORY));
        entity.setOrigin(fieldSet.readString(IDX_ORIGIN));
        entity.setProducerName(fieldSet.readString(IDX_PRODUCER_NAME));
        entity.setReleasedOn(LocalDate.parse(fieldSet.readRawString(IDX_RELEASED_ON)));
//        entity.setReleasedOn(fieldSet.readDate(IDX_RELEASED_ON, "yyyy-MM-dd"));
        entity.setUpdatedAt(LocalDateTime.parse(fieldSet.readRawString(IDX_UPDATED_AT)));
//        entity.setImageUrl(fieldSet.readString(IDX_IMAGE_URL));
//        entity.setVarietal(fieldSet.readString(IDX_VARIETAL));
//        entity.setStyle(fieldSet.readString(IDX_STYLE));
//        entity.setTertiaryCategory(fieldSet.readString(IDX_TERTIARY_CATEGORY));

        return entity;
    }
}