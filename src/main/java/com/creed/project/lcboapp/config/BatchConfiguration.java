package com.creed.project.lcboapp.config;

import com.creed.project.lcboapp.listener.*;
import com.creed.project.lcboapp.mapper.InventoryItemFieldSetMapper;
import com.creed.project.lcboapp.mapper.ProductItemFieldSetMapper;
import com.creed.project.lcboapp.mapper.StoreItemFieldSetMapper;
import com.creed.project.lcboapp.policy.FeedItemSkipPolicy;
import com.creed.project.lcboapp.processor.InventoryItemProcessor;
import com.creed.project.lcboapp.processor.ProductItemProcessor;
import com.creed.project.lcboapp.processor.StoreItemProcessor;
import com.creed.project.lcboapp.tasklet.FeedFileArchivingTasklet;
import com.creed.project.lcboapp.tasklet.FeedFileDownloadingTasklet;
import com.creed.project.lcboapp.tasklet.FeedFileLoadingTasklet;
import com.creed.project.lcboapp.tasklet.FeedFileUnpackingTasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class BatchConfiguration {

    @Autowired
    private Environment environment;

    /**
     * Default Constructor
     */
    public BatchConfiguration() {
        super();
    }

    /* Listeners */

    /**
     * Create description job execution listener bean
     *
     * @return ETLJobExecutionListener
     */
    @Bean
    public ETLJobExecutionListener etlJobExecutionListener() {
        return new ETLJobExecutionListener();
    }

    /**
     * Create description job execution listener bean
     *
     * @return ETLJobExecutionListener
     */
    @Bean
    public FeedFileDownloadingListener feedFileDownloadingListener() {
        return new FeedFileDownloadingListener();
    }

    /**
     * Create description job execution listener bean
     *
     * @return ETLJobExecutionListener
     */
    @Bean
    public FeedFileUnpackingListener feedFileUnpackingListener() {
        return new FeedFileUnpackingListener();
    }

    /**
     * Create description job execution listener bean
     *
     * @return ETLJobExecutionListener
     */
    @Bean
    public FeedDataTransformingListener feedDataTransformingListener() {
        return new FeedDataTransformingListener();
    }

    /**
     * Create description job execution listener bean
     *
     * @return ETLJobExecutionListener
     */
    @Bean
    public FeedFileLoadingListener feedFileLoadingListener() {
        return new FeedFileLoadingListener();
    }

    /**
     * Create description job execution listener bean
     *
     * @return ETLJobExecutionListener
     */
    @Bean
    public FeedFileArchivingListener feedFileArchivingListener() {
        return new FeedFileArchivingListener();
    }

    /* Tasklets */

    /**
     * Create description file listing tasklet bean
     *
     * @return DescriptionFileValidatingTasklet
     */
    @Bean
    public FeedFileDownloadingTasklet feedFileDownloadingTasklet() {
        return new FeedFileDownloadingTasklet();
    }

    /**
     * Create description file listing tasklet bean
     *
     * @return DescriptionFileValidatingTasklet
     */
    @Bean
    public FeedFileUnpackingTasklet feedFileUnpackingTasklet() {
        return new FeedFileUnpackingTasklet();
    }

    /**
     * Create description file listing tasklet bean
     *
     * @return DescriptionFileValidatingTasklet
     */
    @Bean
    public FeedFileLoadingTasklet feedFileLoadingTasklet() {
        return new FeedFileLoadingTasklet();
    }

    /**
     * Create description file listing tasklet bean
     *
     * @return DescriptionFileValidatingTasklet
     */
    @Bean
    public FeedFileArchivingTasklet feedFileArchivingTasklet() {
        return new FeedFileArchivingTasklet();
    }

    /* Processor */

    /**
     * Create Inventory item processor bean
     *
     * @return InventoryItemProcessor
     */
    @Bean
    public InventoryItemProcessor inventoryItemProcessor() {
        return new InventoryItemProcessor();
    }

    /**
     * Create Product item processor bean
     *
     * @return ProductItemProcessor
     */
    @Bean
    public ProductItemProcessor productItemProcessor() {
        return new ProductItemProcessor();
    }

    /**
     * Create Store item processor bean
     *
     * @return StoreItemProcessor
     */
    @Bean
    public StoreItemProcessor storeItemProcessor() {
        return new StoreItemProcessor();
    }

    /* Mappers */

    /**
     * Create Inventory item Mapper bean
     *
     * @return InventoryItemMapper
     */
    @Bean
    public InventoryItemFieldSetMapper inventoryItemFieldSetMapper() {
        return new InventoryItemFieldSetMapper();
    }

    /**
     * Create Product item Mapper bean
     *
     * @return ProductItemMapper
     */
    @Bean
    public ProductItemFieldSetMapper productItemFieldSetMapper() {
        return new ProductItemFieldSetMapper();
    }

    /**
     * Create Store item Mapper bean
     *
     * @return StoreItemMapper
     */
    @Bean
    public StoreItemFieldSetMapper storeItemFieldSetMapper() {
        return new StoreItemFieldSetMapper();
    }

    /**
     * Create Cost Feed Item Skip Policy bean
     *
     * @return FeedItemSkipPolicy
     */
    @Bean
    public FeedItemSkipPolicy feedItemSkipPolicy() {
        return new FeedItemSkipPolicy();
    }
}
