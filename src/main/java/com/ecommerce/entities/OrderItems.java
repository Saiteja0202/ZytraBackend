package com.ecommerce.entities;

import java.time.LocalDate;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.enums.PaymentType;
import com.ecommerce.enums.ShippingStatus;
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
@Table(name = "orderitems")
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderItem;

	@NotNull(message = "Cart Id is required")
	private int cartId;

	@NotNull(message = "User Id is required")
	private int userId;

	@NotNull(message = "Product Id is required")
	private int productId;

	@NotBlank(message = "Brand Name is required")
	private String brandName;

	@NotBlank(message = "Category Name is required")
	private String categoryName;

	@NotBlank(message = "Color is required")
	private String color;

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

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	private ShippingStatus shippingStatus;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	private LocalDate orderDate;

	private int orderId;
	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(int orderItem) {
		this.orderItem = orderItem;
	}

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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	

	

	public OrderItems(int orderItem, @NotNull(message = "Cart Id is required") int cartId,
			@NotNull(message = "User Id is required") int userId,
			@NotNull(message = "Product Id is required") int productId,
			@NotBlank(message = "Brand Name is required") String brandName,
			@NotBlank(message = "Category Name is required") String categoryName,
			@NotBlank(message = "Color is required") String color,
			@NotNull(message = "Total Price is required") long totalPrice,
			@NotBlank(message = "Image is required") String image,
			@NotBlank(message = "Product Description is required") String productDescription,
			@NotBlank(message = "Product Name is required") String productName,
			@NotBlank(message = "Seller Name is required") String sellerName,
			@NotBlank(message = "Size is required") String size,
			@NotBlank(message = "Sub Category Name is required") String subCategoryName,
			@NotNull(message = "Stock Quantity is required") int stockQuantity,
			@NotNull(message = "Product Quantity is required") int productQuantity, OrderStatus orderStatus,
			ShippingStatus shippingStatus, PaymentStatus paymentStatus, PaymentType paymentType, LocalDate orderDate,
			int orderId) {
		super();
		this.orderItem = orderItem;
		this.cartId = cartId;
		this.userId = userId;
		this.productId = productId;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.color = color;
		this.totalPrice = totalPrice;
		this.image = image;
		this.productDescription = productDescription;
		this.productName = productName;
		this.sellerName = sellerName;
		this.size = size;
		this.subCategoryName = subCategoryName;
		this.stockQuantity = stockQuantity;
		this.productQuantity = productQuantity;
		this.orderStatus = orderStatus;
		this.shippingStatus = shippingStatus;
		this.paymentStatus = paymentStatus;
		this.paymentType = paymentType;
		this.orderDate = orderDate;
		this.orderId = orderId;
	}

	public OrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}

}
