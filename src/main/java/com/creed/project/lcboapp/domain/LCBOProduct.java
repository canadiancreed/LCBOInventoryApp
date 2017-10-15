package com.creed.project.lcboapp.domain;

/**
 * Old product files are missing the last four Columns and don't have the same column layout.
 * Will need to do custom readers and identify how to tell the difference.
 *
 * Old format = id,is_dead,name,tags,is_discontinued,price_in_cents,regular_price_in_cents,limited_time_offer_savings_in_cents,limited_time_offer_ends_on,bonus_reward_miles,bonus_reward_miles_ends_on,stock_type,primary_category,secondary_category,origin,package,package_unit_type,package_unit_volume_in_milliliters,total_package_units,total_package_volume_in_milliliters,volume_in_milliliters,alcohol_content,price_per_liter_of_alcohol_in_cents,price_per_liter_in_cents,inventory_count,inventory_volume_in_milliliters,inventory_price_in_cents,sugar_content,producer_name,released_on,has_value_added_promotion,has_limited_time_offer,has_bonus_reward_miles,is_seasonal,is_vqa,is_kosher,value_added_promotion_description,description,serving_suggestion,tasting_note,updated_at
 *
 * New format - id,is_dead,name,tags,is_discontinued,price_in_cents,regular_price_in_cents,limited_time_offer_savings_in_cents,limited_time_offer_ends_on,bonus_reward_miles,bonus_reward_miles_ends_on,stock_type,primary_category,secondary_category,origin,package,package_unit_type,package_unit_volume_in_milliliters,total_package_units,volume_in_milliliters,alcohol_content,price_per_liter_of_alcohol_in_cents,price_per_liter_in_cents,inventory_count,inventory_volume_in_milliliters,inventory_price_in_cents,sugar_content,producer_name,released_on,has_value_added_promotion,has_limited_time_offer,has_bonus_reward_miles,is_seasonal,is_vqa,is_kosher,value_added_promotion_description,description,serving_suggestion,tasting_note,updated_at,image_thumb_url,image_url,varietal,style,tertiary_category,sugar_in_grams_per_liter
 */

public class LCBOProduct {

	private Long id;
	private String name;
	private Double priceInCents;
	private Double regularPriceInCents;
	private String primaryCategory;
	private String secondaryCategory;
	private String origin;
	private String producerName;
	private String releasedOn; //Convert to Date
	private String updatedAt; //Convert to Date
	private String imageUrl;
	private String varietal;
	private String style;
	private String tertiaryCategory;

	/**
	 * Default Constructor
	 */
	public LCBOProduct() { super(); }

    public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPriceInCents() {
		return priceInCents;
	}

	public Double getRegularPriceInCents() {
		return regularPriceInCents;
	}

	public String getPrimaryCategory() {
		return primaryCategory;
	}

	public String getSecondaryCategory() {
		return secondaryCategory;
	}

	public String getOrigin() {
		return origin;
	}

	public String getProducerName() {
		return producerName;
	}

	public String getReleasedOn() {
		return releasedOn;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getImageOrl() {
		return imageUrl;
	}

	public String getVarietal() {
		return varietal;
	}

	public String getStyle() {
		return style;
	}

	public String getTertiaryCategory() {
		return tertiaryCategory;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPriceInCents(Double priceInCents) {
		this.priceInCents = priceInCents;
	}

	public void setRegularPriceInCents(Double regularPriceInCents) {
		this.regularPriceInCents = regularPriceInCents;
	}

	public void setPrimaryCategory(String primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public void setSecondaryCategory(String secondaryCategory) {
		this.secondaryCategory = secondaryCategory;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public void setReleasedOn(String releasedOn) {
		if (releasedOn.isEmpty()) {
			this.releasedOn = null;
		} else {
			this.releasedOn = releasedOn;
		}
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setTertiaryCategory(String tertiaryCategory) {
		this.tertiaryCategory = tertiaryCategory;
	}

	@Override
	public String toString() {
		return "ProductDBModel [id=" + id + ", name=" + name + ", priceInCents=" + priceInCents
				+ ", regularPriceInCents=" + regularPriceInCents + ", primaryCategory=" + primaryCategory
				+ ", secondaryCategory=" + secondaryCategory + ", origin=" + origin + ", producerName=" + producerName
				+ ", releasedOn=" + releasedOn + ", updatedAt=" + updatedAt + ", imageUrl=" + imageUrl + ", varietal="
				+ varietal + ", style=" + style + ", tertiaryCategory=" + tertiaryCategory + "]";
	}
}