package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.repository.LCBOFileRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedFileDownloadingTasklet implements Tasklet, InitializingBean {

    @Autowired
    private LCBOFileRepository lcboFileRepository;

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

        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        String currentZipFileName = lcboFileRepository.downloadLatestLCBODataFile();

        jobContext.put("zipFileName", currentZipFileName);

        return RepeatStatus.FINISHED;
    }
}
