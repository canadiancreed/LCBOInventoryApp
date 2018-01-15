package com.creed.project.lcboapp.tasklet;

import com.creed.project.lcboapp.exception.InvalidNumberOfLCBODataFiles;
import com.creed.project.lcboapp.repository.DataRepository;
import com.creed.project.lcboapp.repository.LCBOFileRepository;
import com.creed.project.lcboapp.repository.TransactionRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedFileUnpackingTasklet implements Tasklet, InitializingBean {

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * @throws Exception if error occurred
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // do nothing
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        String currentZipFileName = (String) jobContext.get("zipFileName");

        lcboFileRepository.unpackLatestLCBODataFile(currentZipFileName);

//        if (lcboFileRepository.size() == 3) {
            transactionRepository.beginTransaction();
//        } else {
//            throw new InvalidNumberOfLCBODataFiles();
//        }

        return RepeatStatus.FINISHED;
    }
}
