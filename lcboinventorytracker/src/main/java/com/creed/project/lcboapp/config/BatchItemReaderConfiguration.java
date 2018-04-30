package com.creed.project.lcboapp.config;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.domain.LCBOInventory;
import com.creed.project.lcboapp.domain.LCBOProduct;
import com.creed.project.lcboapp.domain.LCBOStore;
import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.EnumMap;
import java.util.Map;

/**
 * Reader configurations for various types of data files
 */
@Configuration
public class BatchItemReaderConfiguration {

    @Autowired
    private Environment environment;

    /**
     * Default Constructor
     */
    public BatchItemReaderConfiguration() {
        super();
    }

    /**
     * @return the LCBO Data File Reader EnumMap
     */
    @Bean
    public Map<LCBOFileTypeModel, FlatFileItemReader> feedItemReaderMap(
            @Qualifier("inventoryFeedFileItemReader") FlatFileItemReader<LCBOInventory> inventoryFeedFileItemReader,
            @Qualifier("productFeedFileItemReader") FlatFileItemReader<LCBOProduct> productFeedFileItemReader,
            @Qualifier("storeFeedFileItemReader") FlatFileItemReader<LCBOStore> storeFeedFileItemReader
    ) {

        Map<LCBOFileTypeModel, FlatFileItemReader> feedItemReaderMap = new EnumMap<>(LCBOFileTypeModel.class);

        feedItemReaderMap.put(LCBOFileTypeModel.INVENTORY, inventoryFeedFileItemReader);
        feedItemReaderMap.put(LCBOFileTypeModel.PRODUCT, productFeedFileItemReader);
        feedItemReaderMap.put(LCBOFileTypeModel.STORE, storeFeedFileItemReader);

        return feedItemReaderMap;
    }

    /**
     * Create lcbo inventory flat file item reader bean
     *
     * @return FlatFileItemReader
     */
    @Bean
    public FlatFileItemReader<LCBOInventory> inventoryFeedFileItemReader(
            @Qualifier("inventoryItemFieldSetMapper") FieldSetMapper<LCBOInventory> fieldSetMapper) {

        FlatFileItemReader<LCBOInventory> reader = new FlatFileItemReader<>();

        // set number of lines to skip
        int lineToSkip = environment.getRequiredProperty(Constants.PROP_LCBO_INVENTORY_FEED_LINE_TO_SKIP, Integer.class);
        reader.setLinesToSkip(lineToSkip);

        // instantiate description line mapper
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        DefaultLineMapper<LCBOInventory> lineMapper = new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        // set description line mapper
        reader.setLineMapper(lineMapper);

        return reader;
    }

    /**
     * Create lcbo product flat file item reader bean
     *
     * @return FlatFileItemReader
     */
    @Bean
    public FlatFileItemReader<LCBOProduct> productFeedFileItemReader(
            @Qualifier("productItemFieldSetMapper") FieldSetMapper<LCBOProduct> fieldSetMapper) {

        FlatFileItemReader<LCBOProduct> reader = new FlatFileItemReader<>();

        // set number of lines to skip
        int lineToSkip = environment.getRequiredProperty(Constants.PROP_LCBO_PRODUCT_FEED_LINE_TO_SKIP, Integer.class);
        reader.setLinesToSkip(lineToSkip);

        // instantiate description line mapper
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        DefaultLineMapper<LCBOProduct> lineMapper = new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        // set description line mapper
        reader.setLineMapper(lineMapper);

        return reader;
    }

    /**
     * Create lcbo store flat file item reader bean
     *
     * @return FlatFileItemReader
     */
    @Bean
    public FlatFileItemReader<LCBOStore> storeFeedFileItemReader(
            @Qualifier("storeItemFieldSetMapper") FieldSetMapper<LCBOStore> fieldSetMapper) {

        FlatFileItemReader<LCBOStore> reader = new FlatFileItemReader<>();

        // set number of lines to skip
        int lineToSkip = environment.getRequiredProperty(Constants.PROP_LCBO_STORE_FEED_LINE_TO_SKIP, Integer.class);
        reader.setLinesToSkip(lineToSkip);

        // instantiate description line mapper
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        DefaultLineMapper<LCBOStore> lineMapper = new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        // set description line mapper
        reader.setLineMapper(lineMapper);

        return reader;
    }
}
