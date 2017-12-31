package com.creed.project.lcboapp.persistence.model;

import com.creed.project.lcboapp.domain.model.LCBOData;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * ModelDescriptionEntity
 */
@Entity
@Table(name = "STG_MODEL_LCBO_DATA")
public class ModelLCBODataEntity {

    // Model Cost ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LCBO_DATA_ID", nullable = false, precision = 16)
    private Long modelLCBODataId;

    // transaction Id
    @Column(name = "TRANS_ID")
    private Long transId;

    // feed Id
    @Column(name = "FEED_ID")
    private Long feedId;

    // cost name
    @Column(name = "LCBO_DATA_NAME", nullable = false)
    private String lcboDataName;

    // model code
    @Column(name = "LCBO_DATA", nullable = false, columnDefinition = "CHAR", length = 4)
    private String modelLCBODataCode;

    //This and its accompyning class may not be needed
    @Transient
    private LCBOData lcboData;

    // Model Cost Document (JSON Document)
    @Lob
    @Column(name = "LCBO_DATA_DOC", nullable = false, columnDefinition = "CLOB NOT NULL")
    private String lcboDataDocument;

    // Created Date
    @Column(name = "CREATED_DATE", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private Timestamp createdDate;

    // Updated Date
    @Column(name = "UPDATED_DATE", nullable = false, insertable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    /**
     * Default Constructor
     */
    public ModelLCBODataEntity() {
        super();
    }

    /**
     * @return the model cost Id
     */
    public Long getModelCostId() {
        return modelLCBODataId;
    }

    /**
     * @param modelLCBODataId the model cost Id to set
     */
    public void setModelLCBODataId(final Long modelLCBODataId) {
        this.modelLCBODataId = modelLCBODataId;
    }

    /**
     * @return the feed Id
     */
    public Long getFeedId() {
        return feedId;
    }

    /**
     * @param feedId the feed Id to set
     */
    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    /**
     * @return the transaction Id
     */
    public Long getTransId() {
        return transId;
    }

    /**
     * @param transId the transaction Id to set
     */
    public void setTransId(Long transId) {
        this.transId = transId;
    }

    /**
     * @return the cost name
     */
    public String getLCBODataName() {
        return lcboDataName;
    }

    /**
     * @param lcboDataName the cost name
     */
    public void setLCBODataName(final String lcboDataName) {
        this.lcboDataName = lcboDataName;
    }

    /**
     * @return the model code
     */
    public String getModelLCBODataCode() {
        return modelLCBODataCode;
    }

    /**
     * @param modelLCBODataCode the model code to set
     */
    public void setModelLCBODataCode(final String modelLCBODataCode) {
        this.modelLCBODataCode = modelLCBODataCode;
    }

    /**
     * @return the lcbo data object
     */
    public LCBOData getLCBOData() {
        return lcboData;
    }

    /**
     * @param lcboData the lcbo data object
     */
    public void setLCBOData(final LCBOData lcboData) {
        this.lcboData = lcboData;
    }

    /**
     * @return the model cost document
     */
    public String getLCBODataDocument() {
        return lcboDataDocument;
    }

    /**
     * @param lcboDataDocument the model cost document to set
     */
    public void setLCBODataDocument(final String lcboDataDocument) {
        this.lcboDataDocument = lcboDataDocument;
    }

    /**
     * @return the created date
     */
    public Timestamp getCreatedDate() {
        return createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * @param createdDate the created date to set
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * @return the updated date
     */
    public Timestamp getUpdatedDate() {
        return updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    /**
     * @param updatedDate the updated date to set
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = (Timestamp) updatedDate.clone();
    }


}

