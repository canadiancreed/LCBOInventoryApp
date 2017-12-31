package com.creed.project.lcboapp.config;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.listener.*;
import com.creed.project.lcboapp.tasklet.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Batch Job Configuration
 */
@Configuration
@EnableScheduling
public class BatchJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * Default Constructor
     */
    public BatchJobConfiguration() {
        super();
    }

    /**
     * Create LCBO Data Feed job
     *
     * @param feedFileDownloading
     * @param feedFileUnpacking
     * @param feedInventoryFileTransforming
     * @param feedProductFileTransforming
     * @param feedStoreFileTransforming
     * @param feedFileLoading
     * @param feedFileArchiving
     * @param listener
     * @return importing LCBO Data feed job
     */
    @Bean
    public Job importLCBODataFeed(@Qualifier("feedFileDownloading") Step feedFileDownloading,
                                  @Qualifier("feedFileUnpacking") Step feedFileUnpacking,
                                  @Qualifier("feedFileRetrieving") Step feedFileRetrieving,
                                  @Qualifier("feedInventoryFileTransforming") Step feedInventoryFileTransforming,
                                  @Qualifier("feedProductFileTransforming") Step feedProductFileTransforming,
                                  @Qualifier("feedStoreFileTransforming") Step feedStoreFileTransforming,
                                  @Qualifier("feedFileLoading") Step feedFileLoading,
                                  @Qualifier("feedFileArchiving") Step feedFileArchiving,
                                  @Qualifier("etlJobExecutionListener") ETLJobExecutionListener listener) {

        return jobBuilderFactory.get("LCBO data feed import")
                                .incrementer(new RunIdIncrementer())
                                .listener(listener)

                                .flow(feedFileDownloading)
                                .next(feedFileUnpacking)
                                .next(feedFileRetrieving)

                                .from(feedFileRetrieving).on(Constants.STEP_EXIT_STATUS_COMPLETED)
                                .end()

                                .from(feedFileRetrieving).on(LCBOFileTypeModel.INVENTORY.getName())
                                .to(feedInventoryFileTransforming)
                                .next(feedFileLoading)

                                .from(feedFileRetrieving).on(LCBOFileTypeModel.PRODUCT.getName())
                                .to(feedProductFileTransforming)
                                .next(feedFileLoading)

                                .from(feedFileRetrieving).on(LCBOFileTypeModel.STORE.getName())
                                .to(feedStoreFileTransforming)
                                .next(feedFileLoading)

                                .next(feedFileArchiving)
//                                .next(feedFileDownloading)
                                .build()

                                .build();
    }

    /**
     * Create file downloading step bean
     *
     * @param tasklet  LCBO file downloading tasklet
     * @param listener LCBO file downloading listener
     * @return downloading LCBO data file step
     */
    @Bean
    public Step feedFileDownloading(@Qualifier("feedFileDownloadingTasklet") FeedFileDownloadingTasklet tasklet,
                                    @Qualifier("feedFileDownloadingListener") FeedFileDownloadingListener listener) {
        return stepBuilderFactory.get("Download LCBO Data File")
                .tasklet(tasklet)
                .listener(listener)
                .build();
    }

    /**
     * Create file unpacking step bean
     *1
     * @param tasklet  LCBO file unpacking tasklet
     * @param listener LCBO file unpacking listener
     * @return unpacking LCBO data file step
     */
    @Bean
    public Step feedFileUnpacking(@Qualifier("feedFileUnpackingTasklet") FeedFileUnpackingTasklet tasklet,
                                  @Qualifier("feedFileUnpackingListener") FeedFileUnpackingListener listener) {
        return stepBuilderFactory.get("Unpacking LCBO File Data")
                .tasklet(tasklet)
                .listener(listener)
                .build();
    }

    /**
     * Create file unpacking step bean
     *1
     * @param tasklet  LCBO file unpacking tasklet
     * @param listener LCBO file unpacking listener
     * @return unpacking LCBO data file step
     */
    @Bean
    public Step feedFileRetrieving(@Qualifier("feedFileRetrievingTasklet") FeedFileRetrievingTasklet tasklet,
                                  @Qualifier("feedFileRetrievingListener") FeedFileRetrievingListener listener) {
        return stepBuilderFactory.get("Retrieving LCBO File Data")
                .tasklet(tasklet)
                .listener(listener)
                .build();
    }

    /**
     * Create file data transformation step bean
     *
     * @param tasklet  LCBO file transformation tasklet
     * @param listener LCBO file transformation listener
     * @return transformation LCBO data file step
     */
    @Bean
    public Step feedFileLoading(@Qualifier("feedFileLoadingTasklet") FeedFileLoadingTasklet tasklet,
                                     @Qualifier("feedFileLoadingListener") FeedFileLoadingListener listener) {
        return stepBuilderFactory.get("Loading LCBO File Data")
                .tasklet(tasklet)
                .listener(listener)
                .build();
    }

    /**
     * Create file archiving step bean
     *
     * @param tasklet  LCBO file archiving tasklet
     * @param listener LCBO file archiving listener
     * @return archiving LCBO data file step
     */
    @Bean
    public Step feedFileArchiving(@Qualifier("feedFileArchivingTasklet") FeedFileArchivingTasklet tasklet,
                                  @Qualifier("feedFileArchivingListener") FeedFileArchivingListener listener) {
        return stepBuilderFactory.get("Archiving LCBO File Data")
                .tasklet(tasklet)
                .listener(listener)
                .build();
    }
}
