package com.ecommerce.entities;


import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="products")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	@NotNull(message="Category Id is required")
	private int categoryId;
	
	@NotNull(message="SubCategory Id is required")
	private int subCategoryId;
	
	@NotNull(message="Brand Id is required")
	private int brandId;
	
	@NotNull(message="Seller Id is required")
	private int sellerId;
	
	@NotNull(message="Discount Id is required")
	private int discountId;
	
	@NotNull(message="Admin Id is required")
	private int adminId;
	
	@NotBlank(message="Product Name is required")
	private String productName;
	
	@NotBlank(message="Product Description is required")
	private String productDescription;
	
	@NotNull(message="Actual Price is required")
	private long actualPrice;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@NotBlank(message="Image is required")
	private String image;
	
	private String color;
	
	private String size;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public long getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(long actualPrice) {
		this.actualPrice = actualPrice;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Products(int productId, @NotNull(message = "Category Id is required") int categoryId,
			@NotNull(message = "SubCategory Id is required") int subCategoryId,
			@NotNull(message = "Brand Id is required") int brandId,
			@NotNull(message = "Seller Id is required") int sellerId,
			@NotNull(message = "Discount Id is required") int discountId,
			@NotNull(message = "Admin Id is required") int adminId,
			@NotBlank(message = "Product Name is required") String productName,
			@NotBlank(message = "Product Description is required") String productDescription,
			@NotNull(message = "Actual Price is required") long actualPrice, LocalDateTime createdAt,
			LocalDateTime updatedAt, @NotBlank(message = "Image is required") String image, String color, String size) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.brandId = brandId;
		this.sellerId = sellerId;
		this.discountId = discountId;
		this.adminId = adminId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.actualPrice = actualPrice;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.image = image;
		this.color = color;
		this.size = size;
	}

	
	
	
	
}
