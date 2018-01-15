package com.creed.project.lcboapp.processor;

import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.persistence.model.LCBOInventoryEntity;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import com.creed.project.lcboapp.validator.LCBOInventoryDataFeedValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class InventoryItemProcessor implements ItemProcessor<LCBOInventory, LCBOInventory> {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Default Constructor
     */
    public InventoryItemProcessor() {
        super();
    }

    @Override
    public LCBOInventory process(final LCBOInventory inventory) throws Exception {

        LCBOInventoryDataFeedValidator.validateAllBizRules(inventory);

        LCBOInventoryEntity lcboInventoryEntity = new LCBOInventoryEntity();

        lcboInventoryEntity.setProductID(inventory.getProductID());
        lcboInventoryEntity.setStoreID(inventory.getStoreID());
        lcboInventoryEntity.setQuantity(inventory.getQuantity());
        lcboInventoryEntity.setUpdatedOn(inventory.getUpdatedOn());
        lcboInventoryEntity.setCreatedAt(inventory.getCreatedAt());
        lcboInventoryEntity.setUpdatedAt(inventory.getUpdatedAt());

        dataRepository.addEntity(lcboInventoryEntity);

        return inventory;
    }
}