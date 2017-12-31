package com.creed.project.lcboapp.repository;

import com.creed.project.lcboapp.common.Constants;
import com.creed.project.lcboapp.common.ETLStatus;
import com.creed.project.lcboapp.common.FeedUtils;
import com.creed.project.lcboapp.persistence.model.LCBOAppFeedEntity;
import com.creed.project.lcboapp.persistence.model.LCBOAppTransactionEntity;
import com.creed.project.lcboapp.persistence.repository.LCBOAppFeedJpaRepository;
import com.creed.project.lcboapp.persistence.repository.LCBOAppTransactionJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class TransactionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);
    private static final LCBOAppTransactionEntity transactionEntity = new LCBOAppTransactionEntity();
    private static final LCBOAppFeedEntity feedEntity = new LCBOAppFeedEntity();

    @Autowired
    private Environment environment;

    @Autowired
    private LCBOFileRepository lcboFileRepository;

    @Autowired
    private LCBOAppTransactionJpaRepository jpaRepository;

    @Autowired
    private LCBOAppFeedJpaRepository feedJpaRepository;

    /**
     * Default Constructor
     */
    public TransactionRepository() {
        super();
    }

    /**
     * @return the current transaction Id
     */
    public Long getTransactionId() {
        return transactionEntity.getTransId();
    }

    /**
     * Log Begin of Transaction
     */
    public void beginTransaction() {
        LCBOAppTransactionEntity entity = new LCBOAppTransactionEntity();
        BeanUtils.copyProperties(entity, transactionEntity);
        trailTransaction(ETLStatus.STARTED.name(), ETLStatus.STARTED);
    }

    /**
     * Log the transaction is processing
     *
     * @param loaderStatus the load progress status
     */
    public void processTransaction(final String loaderStatus) {
        if (transactionEntity.getTransId() == null) {
            return;
        }

        trailTransaction(loaderStatus, ETLStatus.PROCESSING);
    }

    /**
     * Log the transaction failed
     *
     * @param loaderStatus the load progress status
     */
    public void failTransaction(final String loaderStatus) {
        if (transactionEntity.getTransId() == null) {
            return;
        }

        trailTransaction(loaderStatus, ETLStatus.FAILED);
    }

    /**
     * Log End of Transaction
     */
    public void endTransaction() {
        if (transactionEntity.getTransId() == null) {
            return;
        }

        trailTransaction(ETLStatus.COMPLETED.name(), ETLStatus.COMPLETED);
    }

    /**
     * @param loaderStatus the load progress status
     * @param etlStatus    the ETL status
     */
    private void trailTransaction(final String loaderStatus, final ETLStatus etlStatus) {
        // Get current time
        String timezone = environment.getRequiredProperty(Constants.PROP_TIME_ZONE);
        Calendar localDateTime = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        Timestamp timestamp = new Timestamp(localDateTime.getTimeInMillis());

        transactionEntity.setReadyForPickup(FeedUtils.getReadyForPickupFlag(etlStatus));
        transactionEntity.setLoaderStatus(loaderStatus);
        transactionEntity.setEtlStatus(etlStatus.name());
        transactionEntity.setUpdatedDate(timestamp);
        transactionEntity.setDptReady(FeedUtils.getReadyForPickupFlag(etlStatus));

        jpaRepository.save(transactionEntity);

        LOGGER.debug("LCBOApp Transaction: {}", transactionEntity);
    }

    /**
     * @return the current transaction feed Id
     */
    public Long getTransactionFeedId() {
        return feedEntity.getFeedId();
    }

    /**
     * @return the current transaction feed file name
     */
    public String getTransactionFeedFileName() {
        return feedEntity.getFeedFileName();
    }

    /**
     * Log Begin of Feed's Transaction
     */
    public void beginFeedTransaction(final String feedId) {
        LCBOAppFeedEntity entity = new LCBOAppFeedEntity();
        BeanUtils.copyProperties(entity, feedEntity);
        trailFeedTransaction(feedId, ETLStatus.STARTED);
    }

    /**
     * Log the feed's transaction is processing
     *
     * @param feedId the feed Id
     */
    public void processFeedTransaction(final String feedId) {
        if (transactionEntity.getTransId() == null) {
            return;
        }

        trailFeedTransaction(feedId, ETLStatus.PROCESSING);
    }

    /**
     * Log the feed's transaction failed
     *
     * @param feedId the feed Id
     */
    public void failFeedTransaction(String feedId) {
        if (transactionEntity.getTransId() == null) {
            return;
        }

        trailFeedTransaction(feedId, ETLStatus.FAILED);
    }

    /**
     * Log End of Feed's Transaction
     */
    public void endFeedTransaction(String feedId) {
        if (transactionEntity.getTransId() == null) {
            return;
        }

        trailFeedTransaction(feedId, ETLStatus.COMPLETED);
    }

    /**
     * @param feedId    the feed Id
     * @param etlStatus the ETL status
     */
    private void trailFeedTransaction(final String feedId, final ETLStatus etlStatus) {
        // Get current time
        String timezone = environment.getRequiredProperty(Constants.PROP_TIME_ZONE);
        Calendar localDateTime = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        Timestamp timestamp = new Timestamp(localDateTime.getTimeInMillis());

        File file = lcboFileRepository.getFeedFile(feedId);
        if (file != null) {
            try {
                String filename = file.getName();
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                Date creationTime = new Date(attr.creationTime().toMillis());
                long transId = transactionEntity.getTransId();

                feedEntity.setFeedFileName(filename);
                feedEntity.setFeedFileCreationTime(creationTime);
                feedEntity.setTransId(transId);
                feedEntity.setFeedStatus(etlStatus.name());
                feedEntity.setUpdatedDate(timestamp);

                feedJpaRepository.save(feedEntity);

                LOGGER.debug("LCBOApp Feed: {}", feedEntity);
            } catch (IOException e) {
                LOGGER.debug("{}", e.getMessage(), e);
            }
        }
    }
}
