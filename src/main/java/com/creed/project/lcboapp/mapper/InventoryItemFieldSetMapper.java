package com.creed.project.lcboapp.mapper;

import com.creed.project.lcboapp.domain.LCBOInventory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindException;

@Scope(value = "step")
public class InventoryItemFieldSetMapper implements FieldSetMapper<LCBOInventory> {

    private static final int IDX_PRODUCT_ID = 0;
    private static final int IDX_STORE_ID = 1;
    private static final int IDX_QUANTITY = 2;
    private static final int IDX_REPORTED_ON = 3;
    private static final int IDX_UPDATED_ON = 4;

    @Override
    public LCBOInventory mapFieldSet(final FieldSet fieldSet) throws BindException {

        LCBOInventory entity = new LCBOInventory();

        entity.setProductID(fieldSet.readInt(IDX_PRODUCT_ID));
        entity.setStoreID(fieldSet.readInt(IDX_STORE_ID));
        entity.setQuantity(fieldSet.readInt(IDX_QUANTITY));
        entity.setReportedOn(fieldSet.readString(IDX_REPORTED_ON));
        entity.setUpdatedAt(fieldSet.readString(IDX_UPDATED_ON));

        return entity;
    }
}