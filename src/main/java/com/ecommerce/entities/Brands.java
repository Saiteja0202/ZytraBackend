package com.ecommerce.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "brands")
public class Brands {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;

	@NotBlank(message = "Brand Name is required")
	private String brandName;

	@NotBlank(message = "Brand Description is required")
	private String brandDescription;
	
	@NotBlank
	private String brandImage;

	public String getBrandImage() {
		return brandImage;
	}

	public void setBrandImage(String brandImage) {
		this.brandImage = brandImage;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}

	public Brands(int brandId, @NotBlank(message = "Brand Name is required") String brandName,
			@NotBlank(message = "Brand Description is required") String brandDescription, @NotBlank String brandImage) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandDescription = brandDescription;
		this.brandImage = brandImage;
	}

	public Brands() {
		super();
		// TODO Auto-generated constructor stub
	}

}
