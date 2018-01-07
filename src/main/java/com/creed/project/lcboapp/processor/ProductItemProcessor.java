package com.creed.project.lcboapp.processor;

import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.persistence.model.LCBOProductEntity;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import com.creed.project.lcboapp.validator.LCBOProductDataFeedValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class ProductItemProcessor implements ItemProcessor<LCBOProduct, LCBOProduct> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryItemProcessor.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Default Constructor
     */
    public ProductItemProcessor() {
        super();
    }

    @Override
    public LCBOProduct process(final LCBOProduct product) throws Exception {
        LCBOProductDataFeedValidator.validateAllBizRules(product);

        LCBOProductEntity lcboProductEntity = new LCBOProductEntity();

        lcboProductEntity.setId(product.getId());
        lcboProductEntity.setName(product.getName());
        lcboProductEntity.setPriceInCents(product.getPriceInCents());
        lcboProductEntity.setRegularPriceInCents(product.getRegularPriceInCents());
        lcboProductEntity.setPrimaryCategory(product.getPrimaryCategory());
        lcboProductEntity.setSecondaryCategory(product.getSecondaryCategory());
        lcboProductEntity.setOrigin(product.getOrigin());
        lcboProductEntity.setProducerName(product.getProducerName());
        lcboProductEntity.setReleasedOn(product.getReleasedOn());
        lcboProductEntity.setUpdatedAt(product.getUpdatedAt());
        lcboProductEntity.setImageUrl(product.getImageOrl());
        lcboProductEntity.setVarietal(product.getVarietal());
        lcboProductEntity.setStyle(product.getStyle());
        lcboProductEntity.setTertiaryCategory(product.getTertiaryCategory());

        dataRepository.addEntity(lcboProductEntity);

        LOGGER.debug("{}", lcboProductEntity.getId());

        return product;
    }
}