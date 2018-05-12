package com.creed.project.lcboapp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private LocalDate releasedOn;
	private LocalDateTime updatedAt;
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

	public LocalDate getReleasedOn() { return releasedOn; }

	public LocalDateTime getUpdatedAt() { return updatedAt; 	}

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

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPriceInCents(final Double priceInCents) {
		this.priceInCents = priceInCents;
	}

	public void setRegularPriceInCents(final Double regularPriceInCents) {
		this.regularPriceInCents = regularPriceInCents;
	}

	public void setPrimaryCategory(final String primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public void setSecondaryCategory(final String secondaryCategory) {
		this.secondaryCategory = secondaryCategory;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	public void setProducerName(final String producerName) {
		this.producerName = producerName;
	}

	public void setReleasedOn(final LocalDate releasedOn) { this.releasedOn = releasedOn; }

	public void setUpdatedAt(final LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setVarietal(final String varietal) {
		this.varietal = varietal;
	}

	public void setStyle(final String style) {
		this.style = style;
	}

	public void setTertiaryCategory(final String tertiaryCategory) {
		this.tertiaryCategory = tertiaryCategory;
	}

	@Override
	public String toString() {
		return "LCBOProduct{" +
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