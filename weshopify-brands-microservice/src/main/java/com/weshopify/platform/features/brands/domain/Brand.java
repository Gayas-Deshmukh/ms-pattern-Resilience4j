package com.weshopify.platform.features.brands.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "brands")
public class Brand extends IdBasedEntity {
	
	@Column(nullable = false, length = 45, unique = true)
	private String name;
	
	@Column(nullable = false, length = 128)
	private String logo;
	
	/*
	 * @ManyToMany
	 * 
	 * @JoinTable( name = "brands_categories", joinColumns = @JoinColumn(name =
	 * "brand_id"), inverseJoinColumns = @JoinColumn(name = "category_id") ) private
	 * Set<Category> categories = new HashSet<>();
	 */
	@Column(length = 2048)
	private String categories;

	public Brand() {
		
	}
	
	public Brand(String name) {
		this.name = name;
		this.logo = "brand-logo.png";
	}

	public Brand(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", categories=" + categories + "]";
	}

	@Transient
	public String getLogoPath() {
		if (this.id == null) return "/images/image-thumbnail.png";
		
		return "/brand-logos/" + this.id + "/" + this.logo;		
	}
}
