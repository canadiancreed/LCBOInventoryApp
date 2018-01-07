package com.creed.project.lcboapp.processor;

import com.creed.project.lcboapp.domain.LCBOStore;
import com.creed.project.lcboapp.persistence.model.LCBOStoreEntity;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import com.creed.project.lcboapp.validator.LCBOStoreDataFeedValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class StoreItemProcessor implements ItemProcessor<LCBOStore, LCBOStore> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreItemProcessor.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Default Constructor
     */
    public StoreItemProcessor() {
        super();
    }

    @Override
    public LCBOStore process(final LCBOStore store) throws Exception {

        LCBOStoreDataFeedValidator.validateAllBizRules(store);

        LCBOStoreEntity lcboStoreEntity = new LCBOStoreEntity();

        lcboStoreEntity.setId(store.getId());
        lcboStoreEntity.setAddressLineOne(store.getAddressLineOne());
        lcboStoreEntity.setAddressLineTwo(store.getAddressLineTwo());
        lcboStoreEntity.setCity(store.getCity());
        lcboStoreEntity.setPostalCode(store.getPostalCode());
        lcboStoreEntity.setLatitude(store.getLatitude());
        lcboStoreEntity.setLongitude(store.getLongitude());
        lcboStoreEntity.setUpdatedAt(store.getUpdatedAt());

        dataRepository.addEntity(lcboStoreEntity);

        LOGGER.debug("{}", lcboStoreEntity.getId());

        return store;
    }
}