package com.creed.project.lcboapp.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
public class LCBOProductEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE_IN_CENTS", nullable = false)
    private Double priceInCents;

    @Column(name = "REGULAR_PRICE_IN_CENTS", nullable = false)
    private Double regularPriceInCents;

    @Column(name = "PRIMARY_CATEGORY", nullable = false)
    private String primaryCategory;

    @Column(name = "SECONDARY_CATEGORY", nullable = false)
    private String secondaryCategory;

    @Column(name = "ORIGIN", nullable = false)
    private String origin;

    @Column(name = "PRODUCER_NAME", nullable = false)
    private String producerName;

    @Column(name = "RELEASE_ON", nullable = false)
    private Date releasedOn;

    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "Varietal", nullable = false)
    private String varietal;

    @Column(name = "STYLE", nullable = false)
    private String style;

    @Column(name = "TERTIARY_CATEGORY", nullable = false)
    private String tertiaryCategory;

    /**
     * Default Constructor
     */
    public LCBOProductEntity() {
        super();
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public Double getPriceInCents() { return priceInCents; }

    public Double getRegularPriceInCents() { return regularPriceInCents; }

    public String getPrimaryCategory() { return primaryCategory; }

    public String getSecondaryCategory() { return secondaryCategory; }

    public String getOrigin() { return origin; }

    public String getProducerName() { return producerName; }

    public Date getReleasedOn() { return releasedOn; }

    public Date getUpdatedAt() { return updatedAt; }

    public String getImageUrl() { return imageUrl; }

    public String getVarietal() { return varietal; }

    public String getStyle() { return style; }

    public String getTertiaryCategory() { return tertiaryCategory; }

    public void setId(final Long id) { this.id = id; }

    public void setName(final String name) { this.name = name; }

    public void setPriceInCents(final Double priceInCents) { this.priceInCents = priceInCents; }

    public void setRegularPriceInCents(final Double regularPriceInCents) { this.regularPriceInCents = regularPriceInCents; }

    public void setPrimaryCategory(final String primaryCategory) { this.primaryCategory = primaryCategory; }

    public void setSecondaryCategory(final String secondaryCategory) { this.secondaryCategory = secondaryCategory; }

    public void setOrigin(final String origin) { this.origin = origin; }

    public void setProducerName(final String producerName) { this.producerName = producerName; }

    public void setReleasedOn(final Date releasedOn) { this.releasedOn = releasedOn; }

    public void setUpdatedAt(final Date updatedAt) { this.updatedAt = updatedAt; }

    public void setImageUrl(final String imageUrl) { this.imageUrl = imageUrl; }

    public void setVarietal(final String varietal) { this.varietal = varietal; }

    public void setStyle(final String style) { this.style = style; }

    public void setTertiaryCategory(final String tertiaryCategory) { this.tertiaryCategory = tertiaryCategory; }

    @Override
    public String toString() {
        return "LCBOProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceInCents=" + priceInCents +
                ", regularPriceInCents=" + regularPriceInCents +
                ", primaryCategory='" + primaryCategory + '\'' +
                ", secondaryCategory='" + secondaryCategory + '\'' +
                ", origin='" + origin + '\'' +
                ", producerName='" + producerName + '\'' +
                ", releasedOn=" + releasedOn +
                ", updatedAt=" + updatedAt +
                ", imageUrl='" + imageUrl + '\'' +
                ", varietal='" + varietal + '\'' +
                ", style='" + style + '\'' +
                ", tertiaryCategory='" + tertiaryCategory + '\'' +
                '}';
    }
}
