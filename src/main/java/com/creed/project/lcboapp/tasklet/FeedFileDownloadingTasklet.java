package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedFileDownloadingTasklet implements Tasklet, InitializingBean {

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Default Constructor
     */
    public FeedFileDownloadingTasklet() {
        super();
    }

    /**
     * @throws Exception if error occurred
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // do nothing
    }

    /**
     * Put the file name into the job execution context
     * <p>
     * Returns RepeatStatus.FINISHED if finished.
     * If not they return RepeatStatus.CONTINUABLE.
     * On failure throws an exception.
     *
     * @param stepContribution mutable state to be passed back to update the current step execution
     * @param chunkContext attributes shared between invocations but not between restarts
     * @return indicating whether processing is continuable.
     * @throws Exception if error occurred while executing
     */
    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {

        lcboFileRepository.downloadLatestLCBODataFile();

        return RepeatStatus.FINISHED;
    }
}
