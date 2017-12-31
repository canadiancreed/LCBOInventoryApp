package com.creed.project.lcboapp.domain;

import java.util.Date;

public class LCBOStore {

	private Long id;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String postalCode;
	private String latitude;
	private String longitude;
	private Date updatedAt;

	/**
	 * Default Constructor
	 */
	public LCBOStore() { super(); }

    public Long getId() {
		return id;
	}

	public String getAddressLineOne() { return addressLineOne; }

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

	public Date getUpdatedAt() {
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

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LCBOStore{");
		sb.append("id=").append(id);
		sb.append(", addressLineOne='").append(addressLineOne).append('\'');
		sb.append(", addressLineTwo='").append(addressLineTwo).append('\'');
		sb.append(", city='").append(city).append('\'');
		sb.append(", postalCode='").append(postalCode).append('\'');
		sb.append(", latitude='").append(latitude).append('\'');
		sb.append(", longitude='").append(longitude).append('\'');
		sb.append(", updatedAt=").append(updatedAt);
		sb.append('}');
		return sb.toString();
	}
}
