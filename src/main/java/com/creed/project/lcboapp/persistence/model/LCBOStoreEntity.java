package com.creed.project.lcboapp.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "STORE")
public class LCBOStoreEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ADDRESS_LINE_ONE", nullable = false)
    private String addressLineOne;

    @Column(name = "ADDRESS_LINE_TWO", nullable = false)
    private String addressLineTwo;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "POSTAL_CODE", nullable = false)
    private String postalCode;

    @Column(name = "LATITUDE", nullable = false)
    private String latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private String longitude;

    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt;

    /**
     * Default Constructor
     */
    public LCBOStoreEntity() {
        super();
    }

    public Long getId() { return id; }

    public String getAddressLineOne() { return addressLineOne; }

    public String getAddressLineTwo() { return addressLineTwo; }

    public String getCity() { return city; }

    public String getPostalCode() { return postalCode; }

    public String getLatitude() { return latitude; }

    public String getLongitude() { return longitude; }

    public Date getUpdatedAt() { return updatedAt; }

    public void setId(final Long id) { this.id = id; }

    public void setAddressLineOne(final String addressLineOne) { this.addressLineOne = addressLineOne; }

    public void setAddressLineTwo(final String addressLineTwo) { this.addressLineTwo = addressLineTwo; }

    public void setCity(final String city) { this.city = city; }

    public void setPostalCode(final String postalCode) { this.postalCode = postalCode; }

    public void setLatitude(final String latitude) { this.latitude = latitude; }

    public void setLongitude(final String longitude) { this.longitude = longitude; }

    public void setUpdatedAt(final Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "LCBOStoreEntity{" +
                "id=" + id +
                ", addressLineOne=" + addressLineOne +
                ", addressLineTwo=" + addressLineTwo +
                ", city=" + city +
                ", postalCode=" + postalCode +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
