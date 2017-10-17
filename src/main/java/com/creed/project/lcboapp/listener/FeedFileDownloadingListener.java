package com.creed.project.lcboapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class FeedFileDownloadingListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileDownloadingListener.class);

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
        LOGGER.debug(">>>>>>>>>> End LCBO Feed File Downloading Step. Time Elasped: ()",
                        System.currentTimeMillis() - timestamp);

        return null;
    }
}
