package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface OrderService {
	
	ResponseEntity<String> addToCart(int userId, int productId,String token);
	ResponseEntity<String> deleteFromCart(int userId, int productId,String token);
	ResponseEntity<?> getProductDetails(int userId, int productId, String token);
}
