package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.domain.model.LCBOFileTypeModel;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class FeedFileRetrievingTasklet implements Tasklet, InitializingBean {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Override
    public RepeatStatus execute(final StepContribution stepContribution, final ChunkContext chunkContext)
            throws Exception {

        lcboFileRepository.loadLCBODataFileRepository();

        String feedId = lcboFileRepository.getNextFeedId();
        LCBOFileTypeModel lcboFileType = lcboFileRepository.getLCBOFileType(feedId);

        ExecutionContext context = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        context.put("feedId", feedId);
        context.put("lcboFileType", lcboFileType);

//        retrieveHeaderInfo(feedId);
//
//        return RepeatStatus.FINISHED;

//        String feedId = lcboFileRepository.getNextFeedId();
//        LCBOFileType lcboFileType = lcboFileRepository.getLCBOFileType(feedId);
//        File feed = lcboFileRepository.getFeedFile(feedId);
//        String feedName = feed == null ? null : feed.getName();
//
//        ExecutionContext context = chunkContext.getStepContext()
//                .getStepExecution()
//                .getJobExecution()
//                .getExecutionContext();
//
//        context.put("feedId", feedId);
//        context.put("feedType", lcboFileType);
//        context.put("feedName", feedName);

        //todo Will this be needed?
//        retrieveHeaderInfo(feedId);

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**
     * @param record the line record
     */
    private static void skipHeader(String record) {
        if (StringUtils.isNotBlank(record)) {
            // Do Nothing
        }
    }

    /**
     * Retrieve Header Info
     *
     * @param feedId the feed Id to get the feed file
     * @throws IOException if error occurred while reading feed file
     */
//    private void retrieveHeaderInfo(final String feedId) throws IOException {
//        File file = lcboFileRepository.getFeedFile(feedId);
//
//        if (file == null) {
//            return;
//        }
//
//        FileReader reader = new FileReader(file);
//
//        try (BufferedReader br = new BufferedReader(reader)) {
//            retrieveColumnData(br.readLine());
//        }
//    }

    /**
     * @param record the line record
     */
//    private void retrieveColumnData(final String record) {
//        dataRepository.setLCBODataFileColumnValues(record);
//    }
}
