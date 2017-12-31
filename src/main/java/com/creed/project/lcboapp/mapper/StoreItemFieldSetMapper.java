package com.creed.project.lcboapp.mapper;

import com.creed.project.lcboapp.domain.LCBOStore;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindException;

@Scope(value = "step")
public class StoreItemFieldSetMapper implements FieldSetMapper<LCBOStore> {

    private static final int IDX_ID = 0;
    private static final int IDX_ADDRESS_LINE_ONE = 4;
    private static final int IDX_ADDRESS_LINE_TWO = 5;
    private static final int IDX_CITY = 6;
    private static final int IDX_POSTAL_CODE = 7;
    private static final int IDX_LATITUDE = 10;
    private static final int IDX_LONGITUDE = 11;
    private static final int IDX_UPDATED_AT = 39;

    @Override
    public LCBOStore mapFieldSet(final FieldSet fieldSet) throws BindException {

        LCBOStore entity = new LCBOStore();

        entity.setId(fieldSet.readLong(IDX_ID));
        entity.setAddressLineOne(fieldSet.readString(IDX_ADDRESS_LINE_ONE));
        entity.setAddressLineTwo(fieldSet.readString(IDX_ADDRESS_LINE_TWO));
        entity.setCity(fieldSet.readString(IDX_CITY));
        entity.setPostalCode(fieldSet.readString(IDX_POSTAL_CODE));
        entity.setLatitude(fieldSet.readString(IDX_LATITUDE));
        entity.setLongitude(fieldSet.readString(IDX_LONGITUDE));
        entity.setUpdatedAt(fieldSet.readDate(IDX_UPDATED_AT));

        return entity;
    }
}