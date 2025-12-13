package com.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import com.ecommerce.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;

	@NotNull(message = "User Id is required")
	private int userId;

	private int paymentId;

	private int shippingId;

	private LocalDate orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(name="Carts")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private List<Cart> carts;
	
	private String address;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getShippingId() {
		return shippingId;
	}

	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	

	public Order(int orderId, @NotNull(message = "User Id is required") int userId, int paymentId, int shippingId,
			LocalDate orderDate, OrderStatus orderStatus, List<Cart> carts, String address) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.paymentId = paymentId;
		this.shippingId = shippingId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.carts = carts;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

}
