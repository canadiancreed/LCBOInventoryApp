package com.creed.project.lcboapp.listener;

import com.creed.project.lcboapp.scheduling.FeedImportJobRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Scope;

import java.util.Calendar;

@Scope(value = "step")
public class ETLJobExecutionListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedImportJobRunnable.class);

    /**
     * Default Constructor
     */
    public ETLJobExecutionListener() {
        super();
    }

    /**
     * Callback before a job executes.
     *
     * @param jobExecution the current JobExecution
     */
    public void beforeJob(final JobExecution jobExecution) {
        Calendar startAt = Calendar.getInstance();
        LOGGER.debug(">>>>>>>>>> Started LCBO File Data Importer Job at {}", startAt.getTime());

        FeedImportJobRunnable.processing.set(true);
    }

    /**
     * Callback after completion of a job.
     * Called after both both successful and failed executions.
     * To perform logic on a particular status, use "if (jobExecution.getStatus() == BatchStatus.X)"
     *
     * @param jobExecution the current JobExecution
     */
    public void afterJob(final JobExecution jobExecution) {
        Calendar startAt = Calendar.getInstance();
        LOGGER.debug(">>>>>>>>>> Ended LCBO File Data Importer Job at {}", startAt.getTime());

        FeedImportJobRunnable.processing.set(false);
    }
}
