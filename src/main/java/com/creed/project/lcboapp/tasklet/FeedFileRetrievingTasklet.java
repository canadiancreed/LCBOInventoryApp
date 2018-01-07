package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class FeedFileRetrievingTasklet implements Tasklet, InitializingBean {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Override
    public RepeatStatus execute(final StepContribution stepContribution, final ChunkContext chunkContext)
            throws Exception {

        String lcboFileID = lcboFileRepository.getNextFeedId();
        LCBOFileTypeModel lcboFileType = lcboFileRepository.getLCBOFileType(lcboFileID);
        File lcboFileResource = lcboFileRepository.getFeedFile(lcboFileID);

        ExecutionContext context = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        context.put("lcboFileID", lcboFileID);
        context.put("lcboFileType", lcboFileType);
        context.put("lcboFileResource", lcboFileResource);

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //no actions performed
    }
}