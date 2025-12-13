package com.ecommerce.entities;

import java.time.LocalDate;

import com.ecommerce.enums.ShippingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping")
public class Shipping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shippingId;

	private int orderId;

	private String address;

	private String trackingNumber;

	private LocalDate arrivingDate;

	@Enumerated(EnumType.STRING)
	private ShippingStatus shippingStatus;

	public int getShippingId() {
		return shippingId;
	}

	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public LocalDate getArrivingDate() {
		return arrivingDate;
	}

	public void setArrivingDate(LocalDate arrivingDate) {
		this.arrivingDate = arrivingDate;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Shipping(int shippingId, int orderId, String address, String trackingNumber, LocalDate arrivingDate,
			ShippingStatus shippingStatus) {
		super();
		this.shippingId = shippingId;
		this.orderId = orderId;
		this.address = address;
		this.trackingNumber = trackingNumber;
		this.arrivingDate = arrivingDate;
		this.shippingStatus = shippingStatus;
	}

	public Shipping() {
		super();
		// TODO Auto-generated constructor stub
	}

}
