package com.ecommerce.entities;

import java.time.LocalDateTime;
import com.ecommerce.enums.SellerStatus;
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
@Table(name = "seller")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sellerId;

	@NotNull(message = "Admin Id is required")
	private int adminId;

	@NotBlank(message = "Seller Name is required")
	private String sellerName;

	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Phone Number is required")
	private String phoneNumber;

	@NotBlank(message = "Address is required")
	private String address;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@Enumerated(EnumType.STRING)
	private SellerStatus sellerStatus = SellerStatus.ACTIVE;

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getSellerStatus() {
		return sellerStatus != null ? sellerStatus.name() : null;
	}

	public void setSellerStatus(SellerStatus sellerStatus) {
		this.sellerStatus = sellerStatus;
	}

	public Seller(int sellerId, int adminId, String sellerName, String email, String phoneNumber, String address,
			LocalDateTime createdAt, LocalDateTime updatedAt, SellerStatus sellerStatus) {
		super();
		this.sellerId = sellerId;
		this.adminId = adminId;
		this.sellerName = sellerName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.sellerStatus = sellerStatus;
	}

	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}

}
