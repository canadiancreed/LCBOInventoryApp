package com.creed.project.lcboapp.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "LCBO_APP_TRANS")
public class LCBOAppTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANS_ID")
    private Long transId;

    @Column(name = "LOADER_STATUS")
    private String loaderStatus;

    @Column(name = "READY_FOR_PICKUP")
    private String readyForPickup;

    @Column(name = "ETL_STATUS")
    private String etlStatus;

    @Column(name = "DPT_READY")
    private String dptReady;

    @Column(name = "CREATED_DATE", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "UPDATED_DATE", insertable = false)
    private Timestamp updatedDate;

    /**
     * Default Constructor
     */
    public LCBOAppTransactionEntity() {
        super();
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
     * @return the Loader Status
     */
    public String getLoaderStatus() {
        return loaderStatus;
    }

    /**
     * @param loaderStatus the Loader Status to set
     */
    public void setLoaderStatus(String loaderStatus) {
        this.loaderStatus = loaderStatus;
    }

    /**
     * @return the Ready For Pickup
     */
    public String getReadyForPickup() {
        return readyForPickup;
    }

    /**
     * @param readyForPickup the Ready For Pickup to set
     */
    public void setReadyForPickup(String readyForPickup) {
        this.readyForPickup = readyForPickup;
    }

    /**
     * @return the ETL Status
     */
    public String getEtlStatus() {
        return etlStatus;
    }

    /**
     * @param etlStatus the ETL Status to set
     */
    public void setEtlStatus(String etlStatus) {
        this.etlStatus = etlStatus;
    }

    /**
     * @return the dptReady
     */
    public String getDptReady() {
        return dptReady;
    }

    /**
     * @param dptReady When the ETL is done processing the data it will make this field with a 'Y'
     *                 which notifies the DPT application that this data is to be used.
     *                 There should only be one record that has this value.
     */
    public void setDptReady(String dptReady) {
        this.dptReady = dptReady;
    }

    /**
     * @return the Created Date
     */
    public Timestamp getCreatedDate() {
        return createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * @param createdDate the Created Date to set
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate == null ? null : (Timestamp) createdDate.clone();
    }

    /**
     * @return the Updated Date
     */
    public Timestamp getUpdatedDate() {
        return updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    /**
     * @param updatedDate the Updated Date to set
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate == null ? null : (Timestamp) updatedDate.clone();
    }

    @Override
    public String toString() {
        return "LCBOAppTransactionEntity{" +
                "transId=" + transId +
                ", loaderStatus='" + loaderStatus + '\'' +
                ", readyForPickup='" + readyForPickup + '\'' +
                ", etlStatus='" + etlStatus + '\'' +
                ", dptReady='" + dptReady + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}