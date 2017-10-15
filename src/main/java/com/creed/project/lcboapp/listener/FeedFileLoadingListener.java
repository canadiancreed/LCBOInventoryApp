package com.creed.project.lcboapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class FeedFileLoadingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileLoadingListener.class);

    /**
     * Default Constructor
     */
    public FeedFileLoadingListener() {
        super();
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> Begin LCBO Feed File loading Step");
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        LOGGER.debug(">>>>>>>>>> End LCBO Feed File loading Step");

        return null;
    }
}
