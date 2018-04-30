package com.creed.project.lcboapp.listener;

import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.util.List;
import java.util.Map;

@Scope(value = "step")
public class FeedDataTransformingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedDataTransformingListener.class);

    private long timestamp;

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private Map<LCBOFileTypeModel, FlatFileItemReader> flatFileItemReaderMap;

    /**
     * Default Constructor
     */
    public FeedDataTransformingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Start LCBO Feed Data Transformation Processing Step");

        timestamp = System.currentTimeMillis();

        // Get data from job execution context
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        String feedId = jobContext.getString("lcboFileID");

        // Validate feedId
        if (feedId == null || feedId.isEmpty()) {
            return;
        }

        // Convert File to Resource
        File file = lcboFileRepository.getFeedFile(feedId);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("file:" + file.getPath());
        Long transactionId = transactionRepository.getTransactionId();
        LCBOFileTypeModel lcboFileType = (LCBOFileTypeModel) jobContext.get("lcboFileType");

        // Debugging Message
        LOGGER.debug("LCBO File Feed Id: {}, LCBO File Type: {}, Trans Id: {}, Resource: {}", feedId, lcboFileType, transactionId, resource);

        // Set the resource for the file item reader
        FlatFileItemReader fileItemReader = flatFileItemReaderMap.get(lcboFileType);
        fileItemReader.setResource(resource);

        transactionRepository.processTransaction(feedId);
        transactionRepository.processFeedTransaction(feedId);
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        // Get feedId from job execution context
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        List<Throwable> exceptions = stepExecution.getFailureExceptions();

        String feedId = (String) jobContext.get("lcboFileID");
        LCBOFileTypeModel lcboFileType = (LCBOFileTypeModel) jobContext.get("lcboFileType");
        Long transactionId = transactionRepository.getTransactionId();

        if (!exceptions.isEmpty()) {
            transactionRepository.failTransaction(feedId);
            transactionRepository.failFeedTransaction(feedId);
        }

        // Debugging Message
        LOGGER.debug("LCBO File Type Feed Id: {}, LCBO File Type: {}, Trans Id: {}, Elapsed: {}",
                feedId, lcboFileType, transactionId, System.currentTimeMillis() - timestamp);
        LOGGER.debug(">>>>>>>>>> End LCBO Feed Data Inventory Processing Step");

        return null;
    }
}