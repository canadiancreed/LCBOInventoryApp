package com.creed.project.lcboapp.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT")
public class LCBOProductEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
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

    @Column(name = "RELEASED_ON")
    private LocalDate releasedOn;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "Varietal")
    private String varietal;

    @Column(name = "STYLE")
    private String style;

    @Column(name = "TERTIARY_CATEGORY")
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

    public LocalDate getReleasedOn() { return releasedOn; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

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

    public void setReleasedOn(final LocalDate releasedOn) { this.releasedOn = releasedOn; }

    public void setUpdatedAt(final LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

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
