package com.ecommerce.dtos;

import java.util.List;

import com.ecommerce.entities.Cart;

public class CartResponse {
	
	private List<Cart> carts;
    private Long totalPrice;
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public CartResponse(List<Cart> carts, Long totalPrice) {
		super();
		this.carts = carts;
		this.totalPrice = totalPrice;
	}
	public CartResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
