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
@Table(name="inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventoryId;
	
	@NotNull(message="Product Id in required")
	private int productId;
	
	@NotNull(message="Stock Quantity is required")
	private int stockQuantity;
	
	@NotBlank(message="Warehouse Location is required")
	private String wareHouseLocation;
	
	@NotNull(message="Seller Id is required")
	private int sellerId;
	
	private LocalDateTime lastUpdatedAt;


	public int getInventoryId() {
		return inventoryId;
	}


	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}


	public int getSellerId() {
		return sellerId;
	}


	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public int getStockQuantity() {
		return stockQuantity;
	}


	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}


	public String getWareHouseLocation() {
		return wareHouseLocation;
	}


	public void setWareHouseLocation(String wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}


	public LocalDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}


	public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}



	public Inventory(int inventoryId, @NotNull(message = "Product Id in required") int productId,
			@NotNull(message = "Stock Quantity is required") int stockQuantity,
			@NotBlank(message = "Warehouse Location is required") String wareHouseLocation,
			@NotNull(message = "Seller Id is required") int sellerId, LocalDateTime lastUpdatedAt) {
		super();
		this.inventoryId = inventoryId;
		this.productId = productId;
		this.stockQuantity = stockQuantity;
		this.wareHouseLocation = wareHouseLocation;
		this.sellerId = sellerId;
		this.lastUpdatedAt = lastUpdatedAt;
	}


	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
