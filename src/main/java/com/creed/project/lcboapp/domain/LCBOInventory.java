package com.creed.project.lcboapp.domain;

//Don't know how to have it so that I can use this without needing a primary unique ID column.
//This table doesn't need them, but no idea how to advance without specifying one.


import java.util.Date;

public class LCBOInventory {

	private Long id;
	private int productID;
	private int storeID;
	private int quantity;
	private Date updatedOn;
	private Date createdAt;
	private Date updatedAt;

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

	public Date getUpdatedOn() { return updatedOn; }

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
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

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LCBOInventory{");
		sb.append("id=").append(id);
		sb.append(", productID=").append(productID);
		sb.append(", storeID=").append(storeID);
		sb.append(", quantity=").append(quantity);
		sb.append(", updatedOn=").append(updatedOn);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append('}');
		return sb.toString();
	}
}
