package com.creed.project.lcboapp.repository;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.domain.LCBOStore;
import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.persistence.model.LCBOInventoryEntity;
import com.creed.project.lcboapp.persistence.model.LCBOProductEntity;
import com.creed.project.lcboapp.persistence.model.LCBOStoreEntity;
import com.creed.project.lcboapp.persistence.repository.LCBOInventoryJpaRepository;
import com.creed.project.lcboapp.persistence.repository.LCBOProductJpaRepository;
import com.creed.project.lcboapp.persistence.repository.LCBOStoreJpaRepository;
import com.creed.project.lcboapp.persistence.repository.ModelLCBODataJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRepository.class);
    private static final Map<String, LCBOInventoryEntity> inventoryEntityMap = new ConcurrentHashMap<>();
    private static final Map<String, LCBOProductEntity> productEntityMap = new ConcurrentHashMap<>();
    private static final Map<String, LCBOStoreEntity> storeEntityMap = new ConcurrentHashMap<>();

    @Autowired
    private Environment environment;

    @Autowired
    private LCBOInventoryJpaRepository lcboIventoryJpaRepository;

    @Autowired
    private LCBOProductJpaRepository lcboProductJpaRepository;

    @Autowired
    private LCBOStoreJpaRepository lcboStoreJpaRepository;

    /**
     * Default Constructor
     */
    public DataRepository() {
        super();
    }

    /**
     * @param entity the LCBO Inventory Entity Object
     */
    public void addEntity(final LCBOInventoryEntity entity) {
        final String uniqueKey = entity.getProductID() + "_" + entity.getStoreID();

        synchronized (this) {
            if (entity != null && StringUtils.isNotBlank(uniqueKey)) {
                inventoryEntityMap.put(uniqueKey, entity);
            }
        }
    }

    /**
     * @param entity the LCBO Product Entity Object
     */
    public void addEntity(final LCBOProductEntity entity) {
        final String uniqueKey = entity.getId().toString();

        synchronized (this) {
            if (entity != null && StringUtils.isNotBlank(uniqueKey)) {
                productEntityMap.put(uniqueKey, entity);
            }
        }
    }

    /**
     * @param entity the LCBO Store Entity Object
     */
    public void addEntity(final LCBOStoreEntity entity) {
        final String uniqueKey = entity.getId().toString();

        synchronized (this) {
            if (entity != null && StringUtils.isNotBlank(uniqueKey)) {
                storeEntityMap.put(uniqueKey, entity);
            }
        }
    }

    /**
     * @param lcboFileType the file type to load data
     */
    public void loadLCBOData(final String lcboFileType) {
        // Get current time
        String timezone = environment.getRequiredProperty(Constants.PROP_TIME_ZONE);
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone(timezone));

        // Debug Message
        LOGGER.debug("Start Loading LCBO FIle Data Document at \"{}\"", now.getTime());

        if (lcboFileType.equalsIgnoreCase(String.valueOf(LCBOFileTypeModel.INVENTORY))) {
            lcboIventoryJpaRepository.save(inventoryEntityMap.values());
            inventoryEntityMap.clear();
        } else if (lcboFileType.equalsIgnoreCase(String.valueOf(LCBOFileTypeModel.PRODUCT))) {
            lcboProductJpaRepository.save(productEntityMap.values());
            productEntityMap.clear();
        } else if (lcboFileType.equalsIgnoreCase(String.valueOf(LCBOFileTypeModel.STORE))) {
            lcboStoreJpaRepository.save(storeEntityMap.values());
            storeEntityMap.clear();
        }

        // Debug Message
        now = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        LOGGER.debug("Ended Loading LCBO FIle Data Document at \"{}\"", now.getTime());
    }
}