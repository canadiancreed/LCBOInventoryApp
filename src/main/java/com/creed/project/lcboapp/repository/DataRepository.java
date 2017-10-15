package com.creed.project.lcboapp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRepository.class);
//    private static final Map<String, ModelCostEntity> modelCostEntityMap = new ConcurrentHashMap<>();
//    private static final List<CostEntryValue> costEntryValues = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private Environment environment;

//    @Autowired
//    private ModelCostJpaRepository costJpaRepository;

    /**
     * Default Constructor
     */
    public DataRepository() {
        super();
    }

    public void extractCostData(Long transactionId) {
    }
}
