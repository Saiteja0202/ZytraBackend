package com.ecommerce.dtos;

public class SubCategoryDetails {
	
	private int subCategoryId;
	
	private String subCategoryName;
	
	private String categoryName;
	
	private String subCategoryDescription;
	
	private String categoryDescription;
	

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}

	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	
	public SubCategoryDetails(int subCategoryId, String subCategoryName, String categoryName,
			String subCategoryDescription, String categoryDescription) {
		super();
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
		this.categoryName = categoryName;
		this.subCategoryDescription = subCategoryDescription;
		this.categoryDescription = categoryDescription;
		
	}

	public SubCategoryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
