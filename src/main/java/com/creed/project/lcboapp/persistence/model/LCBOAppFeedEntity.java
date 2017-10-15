package com.creed.project.lcboapp.persistence.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "COST_FEED")
public class LCBOAppFeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Generated Primary Key And Auto Increment
    @Column(name = "FEED_ID", nullable = false)
    private Long feedId;

    // Feed File Name
    @Column(name = "FEED_FILE_NAME", nullable = false)
    private String feedFileName;

    // Feed File Creation Time
    @Column(name = "FEED_FILE_CREATED_TIME", nullable = false, precision = 24)
    private Date feedFileCreationTime;

    // Feed Status
    @Column(name = "FEED_STATUS", nullable = false, length = 30)
    private String feedStatus;

    // Generated Created Date
    @Column(name = "CREATED_DATE", insertable = false, updatable = false)
    private Timestamp createdDate;

    // Generated Updated Date
    @Column(name = "UPDATED_DATE", insertable = false)
    private Timestamp updatedDate;

    // Generated Transaction Id
    @Column(name = "TRANS_ID", nullable = false)
    private Long transId;

    /**
     * Default Constructor
     */
    public LCBOAppFeedEntity() {
        super();
    }

    /**
     * @return the feedId
     */
    public Long getFeedId() {
        return feedId;
    }

    /**
     * @param feedId the feedId to set
     */
    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    /**
     * @return the feedFileName
     */
    public String getFeedFileName() {
        return feedFileName;
    }

    /**
     * @param feedFileName the feedFileName to set
     */
    public void setFeedFileName(String feedFileName) {
        this.feedFileName = feedFileName;
    }

    /**
     * @return the feedFileCreationTime
     */
    public Date getFeedFileCreationTime() {
        return feedFileCreationTime == null ? null : (Date) feedFileCreationTime.clone();
    }

    /**
     * @param feedFileCreationTime the feedFileCreationTime to set
     */
    public void setFeedFileCreationTime(Date feedFileCreationTime) {
        this.feedFileCreationTime = feedFileCreationTime == null ? null : (Date) feedFileCreationTime.clone();
    }

    /**
     * @return the feedStatus
     */
    public String getFeedStatus() {
        return feedStatus;
    }

    /**
     * @param feedStatus the feedStatus to set
     */
    public void setFeedStatus(String feedStatus) {
        this.feedStatus = feedStatus;
    }

    /**
     * @return the createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * @return the updatedDate
     */
    public Timestamp getUpdatedDate() {
        return updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    /**
     * @return the transId
     */
    public Long getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(Long transId) {
        this.transId = transId;
    }

    @Override
    public String toString() {
        return "CostFeedEntity{" +
                "feedId=" + feedId +
                ", feedFileName='" + feedFileName + '\'' +
                ", feedFileCreationTime=" + feedFileCreationTime +
                ", feedStatus='" + feedStatus + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", transId=" + transId +
                '}';
    }
}

