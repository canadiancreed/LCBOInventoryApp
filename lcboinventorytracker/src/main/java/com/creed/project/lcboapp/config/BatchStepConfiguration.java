package com.creed.project.lcboapp.config;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.domain.LCBOStore;
import com.creed.project.lcboapp.listener.FeedDataTransformingListener;
import com.creed.project.lcboapp.policy.FeedItemSkipPolicy;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class BatchStepConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * Default Constructor
     */
    public BatchStepConfiguration() {
        super();
    }

    /**
     * Create transforming LCBO Inventory feed data step bean
     *
     * @param itemReader     LCBO Inventory feed item reader
     * @param itemProcessor  LCBO Inventory feed item processor
     * @param itemSkipPolicy LCBO Inventory feed item skip policy
     * @param listener       LCBO Inventory feed data transforming listener
     * @return transforming LCBO Inventory feed data step
     */
    @Bean
    public Step feedInventoryFileTransforming(
            @Qualifier("inventoryFeedFileItemReader") FlatFileItemReader<LCBOInventory> itemReader,
            @Qualifier("inventoryItemProcessor") ItemProcessor<LCBOInventory, LCBOInventory> itemProcessor,
            @Qualifier("feedItemSkipPolicy") FeedItemSkipPolicy itemSkipPolicy,
            @Qualifier("feedDataTransformingListener") FeedDataTransformingListener listener) {

        // set chuck size
        int chunkSize = environment.getRequiredProperty(Constants.PROP_DATA_CHUNK_SIZE, Integer.class);

        return stepBuilderFactory.get("LCBO Inventory Data Transforming")
                .<LCBOInventory, LCBOInventory>chunk(chunkSize)
                .reader(itemReader)
                .faultTolerant()
                .skipPolicy(itemSkipPolicy)
                .processor(itemProcessor)
                .listener(listener)
                .build();
    }

    /**
     * Create transforming LCBO Product feed data step bean
     *
     * @param itemReader     LCBO Product feed item reader
     * @param itemProcessor  LCBO Product feed item processor
     * @param itemSkipPolicy LCBO Product feed item skip policy
     * @param listener       LCBO Product feed data transforming listener
     * @return transforming LCBO Product feed data step
     */
    @Bean
    public Step feedProductFileTransforming(
            @Qualifier("productFeedFileItemReader") FlatFileItemReader<LCBOProduct> itemReader,
            @Qualifier("productItemProcessor") ItemProcessor<LCBOProduct, LCBOProduct> itemProcessor,
            @Qualifier("feedItemSkipPolicy") FeedItemSkipPolicy itemSkipPolicy,
            @Qualifier("feedDataTransformingListener") FeedDataTransformingListener listener) {

        // set chuck size
        int chunkSize = environment.getRequiredProperty(Constants.PROP_DATA_CHUNK_SIZE, Integer.class);

        return stepBuilderFactory.get("LCBO Product Data Transforming")
                .<LCBOProduct, LCBOProduct>chunk(chunkSize)
                .reader(itemReader)
                .faultTolerant()
                .skipPolicy(itemSkipPolicy)
                .processor(itemProcessor)
                .listener(listener)
                .build();
    }

    /**
     * Create transforming LCBO Store feed data step bean
     *
     * @param itemReader     LCBO Store feed item reader
     * @param itemProcessor  LCBO Store feed item processor
     * @param itemSkipPolicy LCBO Store feed item skip policy
     * @param listener       LCBO Store feed data transforming listener
     * @return transforming LCBO Store feed data step
     */
    @Bean
    public Step feedStoreFileTransforming(
            @Qualifier("storeFeedFileItemReader") FlatFileItemReader<LCBOStore> itemReader,
            @Qualifier("storeItemProcessor") ItemProcessor<LCBOStore, LCBOStore> itemProcessor,
            @Qualifier("feedItemSkipPolicy") FeedItemSkipPolicy itemSkipPolicy,
            @Qualifier("feedDataTransformingListener") FeedDataTransformingListener listener) {

        // set chuck size
        int chunkSize = environment.getRequiredProperty(Constants.PROP_DATA_CHUNK_SIZE, Integer.class);

        return stepBuilderFactory.get("LCBO Store Data Transforming")
                .<LCBOStore, LCBOStore>chunk(chunkSize)
                .reader(itemReader)
                .faultTolerant()
                .skipPolicy(itemSkipPolicy)
                .processor(itemProcessor)
                .listener(listener)
                .build();
    }
}