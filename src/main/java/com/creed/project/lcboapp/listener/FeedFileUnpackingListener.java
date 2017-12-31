package com.creed.project.lcboapp.listener;

import com.creed.project.lcboapp.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class FeedFileUnpackingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileUnpackingListener.class);

    @Autowired
    private TransactionRepository transactionRepository;

    private long timestamp;

    /**
     * Default Constructor
     */
    public FeedFileUnpackingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Begin LCBO Feed File Unpacking Step");

        timestamp = System.currentTimeMillis();
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {

        LOGGER.debug("LCBO File Type Feed Elapsed: {}", System.currentTimeMillis() - timestamp);
        LOGGER.debug(">>>>>>>>>> End LCBO Feed File unpacking Step.");

        return ExitStatus.COMPLETED;
    }
}
