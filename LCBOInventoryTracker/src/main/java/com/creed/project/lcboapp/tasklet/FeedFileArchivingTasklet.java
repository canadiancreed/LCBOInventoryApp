package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;

public class FeedFileArchivingTasklet implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileArchivingTasklet.class);

    @Autowired
    private Environment environment;

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    /**
     * Default Constructor
     */
    public FeedFileArchivingTasklet() {
        super();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // do nothing
    }

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {

        // get feedId from job execution context
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        String feedId = (String) jobContext.get("lcboFileID");
        String currentZipFileName = (String) jobContext.get("zipFileName");

        // remove description object from description repository
        File file = lcboFileRepository.remove(feedId);

        // Archive the file and delete the description file
        if (file != null) {
            LOGGER.debug("Remove LCBO Feed with feed Id {}, {}", feedId, file.getPath());
        } else {
            LOGGER.debug("Remove LCBO Feed with feed Id {}", feedId);
        }

        //If there are no more files to process, archive the zip file
        if (lcboFileRepository.size() == 0) {
            File currentZipFile = new File(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_DOWNLOAD_DIR)
                                            + File.separator + currentZipFileName + ".zip");
            File newZipFile = new File(environment.getRequiredProperty(Constants.PROP_LCBO_FEED_ARCHIVE_DIR)
                                            + File.separator + currentZipFileName + ".zip");

            FileUtils.moveFile(currentZipFile, newZipFile);

            if (newZipFile.exists() && !currentZipFile.exists()) {
                LOGGER.debug("Current LCBO Data file successfully archived");
            } else {
                LOGGER.debug("Current LCBO Data file archive failed.");
            }
        }

        return RepeatStatus.FINISHED;
    }
}