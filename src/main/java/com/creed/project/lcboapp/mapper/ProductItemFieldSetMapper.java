package com.creed.project.lcboapp.mapper;

import com.creed.project.lcboapp.domain.LCBOProduct;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindException;

@Scope(value = "step")
public class ProductItemFieldSetMapper implements FieldSetMapper<LCBOProduct> {

    private static final int IDX_NAME = 0;
    private static final int IDX_PRICE_IN_CENTS = 1;
    private static final int IDX_REGULAR_PRICE_IN_CENTS = 2;
    private static final int IDX_PRIMARY_CATEGORY = 3;
    private static final int IDX_SECONDARY_CATEGORY = 4;
    private static final int IDX_ORIGIN = 5;
    private static final int IDX_PRODUCER_NAME = 6;
    private static final int IDX_RELEASED_ON = 7;
    private static final int IDX_UPDATED_AT = 8;
    private static final int IDX_IMAGE_URL = 9;
    private static final int IDX_VARIETAL = 10;
    private static final int IDX_STYLE = 11;
    private static final int IDX_TERTIARY_CATEGORY = 12;

    @Override
    public LCBOProduct mapFieldSet(final FieldSet fieldSet) throws BindException {

        LCBOProduct entity = new LCBOProduct();

        entity.setName(fieldSet.readString(IDX_NAME));
        entity.setPriceInCents(fieldSet.readDouble(IDX_PRICE_IN_CENTS));
        entity.setRegularPriceInCents(fieldSet.readDouble(IDX_REGULAR_PRICE_IN_CENTS));
        entity.setPrimaryCategory(fieldSet.readString(IDX_PRIMARY_CATEGORY));
        entity.setSecondaryCategory(fieldSet.readString(IDX_SECONDARY_CATEGORY));
        entity.setOrigin(fieldSet.readString(IDX_ORIGIN));
        entity.setProducerName(fieldSet.readString(IDX_PRODUCER_NAME));
        entity.setReleasedOn(fieldSet.readString(IDX_RELEASED_ON));
        entity.setUpdatedAt(fieldSet.readString(IDX_UPDATED_AT));
        entity.setImageUrl(fieldSet.readString(IDX_IMAGE_URL));
        entity.setVarietal(fieldSet.readString(IDX_VARIETAL));
        entity.setStyle(fieldSet.readString(IDX_STYLE));
        entity.setTertiaryCategory(fieldSet.readString(IDX_TERTIARY_CATEGORY));

        return entity;
    }
}