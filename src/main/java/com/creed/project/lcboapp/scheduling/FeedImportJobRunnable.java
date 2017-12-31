package com.creed.project.lcboapp.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class FeedImportJobRunnable {

    public static final AtomicBoolean processing = new AtomicBoolean(false);

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedImportJobRunnable.class);

    private JobLauncher jobLauncher;
    private Job importLCBOFeed;

    /**
     * Constructor
     *
     * @param jobLauncher    the job launcher to launch job
     * @param importLCBOFeed the LCBO feed import job
     */
    public FeedImportJobRunnable(final JobLauncher jobLauncher, final Job importLCBOFeed) {
        this.jobLauncher = jobLauncher;
        this.importLCBOFeed = importLCBOFeed;
    }

    /**
     * Runs Cost Feed Import Job using Cron Expression
     */
    @Scheduled(cron = "${importer.lcbo.cron.schedule}")
    public void runLCBOImportJob() {
        if (!processing.get()) {
            processing.set(true);
            launchLCBOFeedImportJob();
        } else {
            LOGGER.debug("Job execution already running...");
        }
    }

    /**
     * Runs LCBO Feed Import Job
     */
    private void launchLCBOFeedImportJob() {
        try {
            // Add timestamp as a job parameter to make the job unique
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            Calendar startAt = Calendar.getInstance();
            LOGGER.debug("Starting LCBO Importer at {}", startAt.getTime());

            // Run Job
            jobLauncher.run(importLCBOFeed, jobParameters);

        } catch (JobExecutionAlreadyRunningException e) {
            // Job execution already running:
            LOGGER.error("Job execution already running:", e);
        } catch (JobRestartException e) {
            // Illegal attempt to restart a job
            LOGGER.error("Illegal attempt to restart a job:", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            // Illegal attempt to restart a job that was already completed successfully"
            LOGGER.error("Illegal attempt to restart a job that was already completed successfully", e);
        } catch (JobParametersInvalidException e) {
            // Invalid job parameter:
            LOGGER.error("Invalid job parameter:", e);
        }
    }
}
