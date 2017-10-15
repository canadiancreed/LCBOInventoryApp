package com.creed.project.lcboapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class FeedDataTransformingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedDataTransformingListener.class);

    /**
     * Default Constructor
     */
    public FeedDataTransformingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Start LCBO Feed Data Transforming Step");
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> End LCBO Feed Data Transforming Step");

        return null;
    }
}