package com.creed.project.lcboapp.listener;

import com.creed.project.lcboapp.common.ETLStatus;
import com.creed.project.lcboapp.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Scope(value = "step")
public class FeedFileLoadingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileLoadingListener.class);

    @Autowired
    private TransactionRepository transactionRepository;

    private long timestamp;

    /**
     * Default Constructor
     */
    public FeedFileLoadingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Begin LCBO Feed File loading Step");

        timestamp = System.currentTimeMillis();
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        // Get feedId from job execution context
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        List<Throwable> exceptions = stepExecution.getFailureExceptions();
        String lcboFeedID = (String) jobContext.get("lcboFeedID");
        Long transactionId = transactionRepository.getTransactionId();

        if (!exceptions.isEmpty()) {
            transactionRepository.failTransaction(ETLStatus.FAILED.name());
            transactionRepository.failFeedTransaction(lcboFeedID);
        } else {
            transactionRepository.endFeedTransaction(lcboFeedID);
        }

        // Debugging Message
        LOGGER.debug("Loading LCBO Feed Id: {}, Trans Id: {}, Elapsed: {}",
                lcboFeedID, transactionId, System.currentTimeMillis() - timestamp);
        LOGGER.debug(">>>>>>>>>> End LCBO Feed File loading Step");

        return null;
    }
}
