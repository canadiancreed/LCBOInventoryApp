package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.common.FeedUtils;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedFileArchivingTasklet implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedFileArchivingTasklet.class);

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
        ExecutionContext context = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        String feedId = (String) context.get("feedId");

        // remove description object from description repository
        lcboFileRepository.remove(feedId);

        // Archive the file and delete the description file
        LOGGER.debug("Remove LCBO Data Feed with feed Id {}", feedId);

        return RepeatStatus.FINISHED;
    }
}