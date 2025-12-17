package com.ecommerce.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "subcategory")
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int subCategoryId;

	@NotNull(message = "Category Id is required")
	private int categoryId;

	@NotBlank(message = "SubCategory Name is required")
	private String subCategoryName;

	@NotBlank(message = "SubCategory Description is required")
	private String subCategoryDescription;
	
	
	
	

	

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}

	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}

	

	public SubCategory(int subCategoryId, @NotNull(message = "Category Id is required") int categoryId,
			@NotBlank(message = "SubCategory Name is required") String subCategoryName,
			@NotBlank(message = "SubCategory Description is required") String subCategoryDescription
		) {
		super();
		this.subCategoryId = subCategoryId;
		this.categoryId = categoryId;
		this.subCategoryName = subCategoryName;
		this.subCategoryDescription = subCategoryDescription;
	}

	public SubCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

}
