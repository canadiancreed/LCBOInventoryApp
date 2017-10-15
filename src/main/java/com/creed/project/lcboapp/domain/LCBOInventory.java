package com.creed.project.lcboapp.domain;

//Don't know how to have it so that I can use this without needing a primary unique ID column.
//This table doesn't need them, but no idea how to advance without specifying one.

public class LCBOInventory {

	private Long id;
	private int productID;
	private int storeID;
	private int quantity;
	private String reportedOn; //Convert to Date
	private String updatedAt; //Convert to Date

	/**
	 * Default Constructor
	 */
	public LCBOInventory() { super(); }

    public Long getId() { return id; }
		
	public int getProductID() {
		return productID;
	}

	public int getStoreID() {
		return storeID;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getReportedOn() {
		return reportedOn;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

    public void setId(final Long id) { this.id = id; }

	public void setProductID(final int productID) {
		this.productID = productID;
	}

	public void setStoreID(final int storeID) {
		this.storeID = storeID;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	public void setReportedOn(final String reportedOn) {
		this.reportedOn = reportedOn;
	}

	public void setUpdatedAt(final String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "InventoryDBModel [productID=" + productID + ", storeID=" + storeID + ", quantity=" + quantity
				+ ", reportedOn=" + reportedOn + ", updatedAt=" + updatedAt + "]";
	}
}
