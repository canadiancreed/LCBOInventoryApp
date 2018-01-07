package com.creed.project.lcboapp.listener;

import com.creed.project.lcboapp.common.Constants;
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
public class FeedFileRetrievingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileRetrievingListener.class);

    @Autowired
    private TransactionRepository transactionRepository;

    private long timestamp;

    /**
     * Default Constructor
     */
    public FeedFileRetrievingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Begin LCBO Feed File Retrieving Step");

        timestamp = System.currentTimeMillis();
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {

        // Get feedId from job execution context
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        List<Throwable> exceptions = stepExecution.getFailureExceptions();

        String lcboFileID = (String) jobContext.get("lcboFileID");
        LCBOFileTypeModel lcboFileType = (LCBOFileTypeModel) jobContext.get("lcboFileType");
        Long transactionId = transactionRepository.getTransactionId();

        ExitStatus exitStatus;

        if (!exceptions.isEmpty()) {
            transactionRepository.failTransaction(lcboFileID);
            exitStatus = new ExitStatus(Constants.STEP_EXIT_STATUS_FAILED);
        } else {
            if (lcboFileID == null || lcboFileID.isEmpty()) {
                transactionRepository.endTransaction();
                exitStatus = new ExitStatus(Constants.STEP_EXIT_STATUS_COMPLETED);
            } else {
                transactionRepository.beginFeedTransaction(lcboFileID);
                exitStatus = new ExitStatus(lcboFileType.getName());
            }
        }

        LOGGER.debug("LCBO File Type Feed Id: {}, LCBO File Type: {}, Trans Id: {}, Elapsed: {}",
                lcboFileID, lcboFileType, transactionId, System.currentTimeMillis() - timestamp);
        LOGGER.debug(">>>>>>>>>> End LCBO Feed File Retrieving Step.");

        return exitStatus;
    }
}
