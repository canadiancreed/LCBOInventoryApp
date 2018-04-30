package com.creed.project.lcboapp.mapper;

import com.creed.project.lcboapp.domain.LCBOInventory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Scope(value = "step")
public class InventoryItemFieldSetMapper implements FieldSetMapper<LCBOInventory> {

    private static final int IDX_PRODUCT_ID = 0;
    private static final int IDX_STORE_ID = 1;
    private static final int IDX_QUANTITY = 3;
    private static final int IDX_UPDATED_ON = 4;
    private static final int IDX_CREATED_AT = 5;
    private static final int IDX_UPDATED_AT = 6;

    @Override
    public LCBOInventory mapFieldSet(final FieldSet fieldSet) throws BindException {

        LCBOInventory entity = new LCBOInventory();

        entity.setProductID(fieldSet.readInt(IDX_PRODUCT_ID));
        entity.setStoreID(fieldSet.readInt(IDX_STORE_ID));
        entity.setQuantity(fieldSet.readInt(IDX_QUANTITY));
        entity.setUpdatedOn(LocalDate.parse(fieldSet.readRawString(IDX_UPDATED_ON)));
        entity.setCreatedAt(parseDateTime(fieldSet.readRawString(IDX_CREATED_AT)));
        entity.setUpdatedAt(parseDateTime(fieldSet.readRawString(IDX_UPDATED_AT)));

        return entity;
    }

    private LocalDateTime parseDateTime(final String localDateTimeString) {

        String[] localDateTimeParsed = localDateTimeString.split(Pattern.quote("."));

        return LocalDateTime.parse(localDateTimeParsed[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}