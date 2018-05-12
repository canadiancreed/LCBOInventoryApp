package com.creed.project.lcboapp.processor;

import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.persistence.model.LCBOProductEntity;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import com.creed.project.lcboapp.validator.LCBOProductDataFeedValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class ProductItemProcessor implements ItemProcessor<LCBOProduct, LCBOProduct> {

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

        //Addresses if name is blank, which we get for some reason
        if (product.getName().isEmpty()) {
            lcboProductEntity.setName("Unknown");
        } else {
            lcboProductEntity.setName(product.getName());
        }

        lcboProductEntity.setPriceInCents(product.getPriceInCents());
        lcboProductEntity.setRegularPriceInCents(product.getRegularPriceInCents());
        lcboProductEntity.setPrimaryCategory(product.getPrimaryCategory());
        lcboProductEntity.setSecondaryCategory(product.getSecondaryCategory());
        lcboProductEntity.setOrigin(product.getOrigin());
        lcboProductEntity.setProducerName(product.getProducerName());

        //Sometimes ReleaseOn date can be blank, which we've given the value 0999-12-31. Convert to null for DB
        if (product.getReleasedOn().getYear() == 999) {
            lcboProductEntity.setReleasedOn(null);
        } else {
            lcboProductEntity.setReleasedOn(product.getReleasedOn());
        }

        lcboProductEntity.setUpdatedAt(product.getUpdatedAt());

        //New product files only
        lcboProductEntity.setImageUrl(product.getImageOrl());
        lcboProductEntity.setVarietal(product.getVarietal());
        lcboProductEntity.setStyle(product.getStyle());
        lcboProductEntity.setTertiaryCategory(product.getTertiaryCategory());

        dataRepository.addEntity(lcboProductEntity);

        return product;
    }
}