package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.repository.DataRepository;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class FeedFileLoadingTasklet implements Tasklet, InitializingBean {

    @Autowired
    private DataRepository dataRepository;

    /**
     * Default Constructor
     */
    public FeedFileLoadingTasklet() {
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
     * Implementations return RepeatStatus.FINISHED if finished.
     * If not they return RepeatStatus.CONTINUABLE.
     * On failure throws an exception.
     *
     * @param chunkContext attributes shared between invocations but not between restarts
     * @return indicating whether processing is continuable.
     * @throws Exception if error occurred while executing
     */
    @Override
    public RepeatStatus execute(final StepContribution stepContribution, final ChunkContext chunkContext) throws Exception {

        StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();

        LCBOFileTypeModel lcboFileType = (LCBOFileTypeModel) jobContext.get("lcboFileType");

        dataRepository.loadLCBOData(lcboFileType.getName());

        return RepeatStatus.FINISHED;
    }
}