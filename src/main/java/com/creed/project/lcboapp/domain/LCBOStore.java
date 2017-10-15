package com.creed.project.lcboapp.domain;

public class LCBOStore {

	private Long id;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String postalCode;
	private String latitude;
	private String longitude;
	private String updatedAt; //Convert to Date

	/**
	 * Default Constructor
	 */
	public LCBOStore() { super(); }

    public Long getId() {
		return id;
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setAddressLineOne(final String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public void setAddressLineTwo(final String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public void setLatitude(final String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}

	public void setUpdatedAt(final String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "StoreDBModel [id=" + id + ", addressLineOne=" + addressLineOne + ", city=" + city 
				+ ", postalCode=" + postalCode + ", latitude=" + latitude + ", longitude="
				+ longitude + ", updatedAt=" + updatedAt + "]";
	}
}
