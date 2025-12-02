package com.ecommerce.dtos;

import java.time.LocalDateTime;

import com.ecommerce.enums.DiscountTypes;
import com.ecommerce.enums.SellerStatus;

public class AllProducts {
	
	
	private int productId;
	
	private long actualPrice;
	
	private String brandName;
	
	private String categoryName;
	
	private String color;
	
	private int discountValue;
	
	private DiscountTypes discountType;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private long totalPrice;
	
	private String image;
	
	private String productDescription;
	
	private String productName;
	
	private String sellerName;
	
	private SellerStatus sellerStatus;
	
	private String size;
	
	private String subCategoryName;
	
	private int stockQuantity;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public long getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(long actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(int discountValue) {
		this.discountValue = discountValue;
	}

	public DiscountTypes getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountTypes discountType) {
		this.discountType = discountType;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public SellerStatus getSellerStatus() {
		return sellerStatus;
	}

	public void setSellerStatus(SellerStatus sellerStatus) {
		this.sellerStatus = sellerStatus;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public AllProducts(int productId, long actualPrice, String brandName, String categoryName, String color,
			int discountValue, DiscountTypes discountType, LocalDateTime startDate, LocalDateTime endDate,
			long totalPrice, String image, String productDescription, String productName, String sellerName,
			SellerStatus sellerStatus, String size, String subCategoryName, int stockQuantity) {
		super();
		this.productId = productId;
		this.actualPrice = actualPrice;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.color = color;
		this.discountValue = discountValue;
		this.discountType = discountType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalPrice = totalPrice;
		this.image = image;
		this.productDescription = productDescription;
		this.productName = productName;
		this.sellerName = sellerName;
		this.sellerStatus = sellerStatus;
		this.size = size;
		this.subCategoryName = subCategoryName;
		this.stockQuantity = stockQuantity;
	}

	public AllProducts() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
