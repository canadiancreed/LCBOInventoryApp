package com.creed.project.lcboapp.listener;

import com.creed.project.lcboapp.common.ETLStatus;
import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
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
public class FeedFileDownloadingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileDownloadingListener.class);

    @Autowired
    private TransactionRepository transactionRepository;

    private long timestamp;

    /**
     * Default Constructor
     */
    public FeedFileDownloadingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Begin LCBO Feed File Downloading Step");

        timestamp = System.currentTimeMillis();
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        // Get feedId from job execution context

        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        List<Throwable> exceptions = stepExecution.getFailureExceptions();

        String feedId = (String) jobContext.get("feedId");
        LCBOFileTypeModel lcboFileType = (LCBOFileTypeModel) jobContext.get("lcboFileType");
        Long transactionId = transactionRepository.getTransactionId();

        if (!exceptions.isEmpty()) {
            transactionRepository.failTransaction(ETLStatus.UNKNOWN.toString());
        }

        LOGGER.debug("LCBO File Type Feed Id: {}, LCBO File Type: {}, Trans Id: {}, Elapsed: {}",
                feedId, lcboFileType, transactionId, System.currentTimeMillis() - timestamp);
        LOGGER.debug(">>>>>>>>>> End LCBO Feed File Downloading Step.");

        return null;
    }
}
