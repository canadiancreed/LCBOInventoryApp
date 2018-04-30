package com.creed.project.lcboapp.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "INVENTORY")
public class LCBOInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Generated Primary Key And Auto Increment
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PRODUCT_ID", nullable = false)
    private int productID;

    @Column(name = "STORE_ID", nullable = false)
    private int storeID;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "UPDATED_ON", nullable = false)
    private LocalDate updatedOn;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Default Constructor
     */
    public LCBOInventoryEntity() {
        super();
    }

    public Long getId() { return id; }

    public int getProductID() { return productID; }

    public int getStoreID() { return storeID; }

    public int getQuantity() { return quantity; }

    public LocalDate getUpdatedOn() { return updatedOn; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(final Long id) { this.id = id; }

    public void setProductID(final int productID) { this.productID = productID; }

    public void setStoreID(final int storeID) { this.storeID = storeID; }

    public void setQuantity(final int quantity) { this.quantity = quantity; }

    public void setUpdatedOn(final LocalDate updatedOn) { this.updatedOn = updatedOn; }

    public void setCreatedAt(final LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(final LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "LCBOInventoryEntity{" +
                "id=" + id +
                ", productID=" + productID +
                ", storeID=" + storeID +
                ", quantity=" + quantity +
                ", updatedOn=" + updatedOn +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
