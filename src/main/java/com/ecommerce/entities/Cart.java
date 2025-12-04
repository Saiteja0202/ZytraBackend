package com.ecommerce.entities;

import java.time.LocalDateTime;
import com.ecommerce.enums.DiscountTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

	@NotNull(message = "User Id is required")
	private int userId;

	@NotNull(message = "Product Id is required")
	private int productId;

	@NotNull(message = "Actual Price is required")
	private long actualPrice;

	@NotBlank(message = "Brand Name is required")
	private String brandName;

	@NotBlank(message = "Category Name is required")
	private String categoryName;

	@NotBlank(message = "Color is required")
	private String color;

	@NotNull(message = "Discount Value is required")
	private int discountValue;

	@Enumerated(EnumType.STRING)
	private DiscountTypes discountType;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@NotNull(message = "Total Price is required")
	private long totalPrice;

	@NotBlank(message = "Image is required")
	private String image;

	@NotBlank(message = "Product Description is required")
	private String productDescription;

	@NotBlank(message = "Product Name is required")
	private String productName;

	@NotBlank(message = "Seller Name is required")
	private String sellerName;


	@NotBlank(message = "Size is required")
	private String size;

	@NotBlank(message = "Sub Category Name is required")
	private String subCategoryName;

	@NotNull(message = "Stock Quantity is required")
	private int stockQuantity;

	@NotNull(message = "Product Quantity is required")
	private int productQuantity;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Cart(int cartId, @NotNull(message = "User Id is required") int userId,
			@NotNull(message = "Product Id is required") int productId,
			@NotNull(message = "Actual Price is required") long actualPrice,
			@NotBlank(message = "Brand Name is required") String brandName,
			@NotBlank(message = "Category Name is required") String categoryName,
			@NotBlank(message = "Color is required") String color,
			@NotNull(message = "Discount Value is required") int discountValue, DiscountTypes discountType,
			LocalDateTime startDate, LocalDateTime endDate,
			@NotNull(message = "Total Price is required") long totalPrice,
			@NotBlank(message = "Image is required") String image,
			@NotBlank(message = "Product Description is required") String productDescription,
			@NotBlank(message = "Product Name is required") String productName,
			@NotBlank(message = "Seller Name is required") String sellerName,
			@NotBlank(message = "Size is required") String size,
			@NotBlank(message = "Sub Category Name is required") String subCategoryName,
			@NotNull(message = "Stock Quantity is required") int stockQuantity,
			@NotNull(message = "Product Quantity is required") int productQuantity) {
		super();
		this.cartId = cartId;
		this.userId = userId;
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
		this.size = size;
		this.subCategoryName = subCategoryName;
		this.stockQuantity = stockQuantity;
		this.productQuantity = productQuantity;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

}
