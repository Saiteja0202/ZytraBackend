package com.ecommerce.service;

import org.springframework.http.ResponseEntity;
import com.ecommerce.entities.Payments;

public interface OrderService {
	
	ResponseEntity<String> addToCart(int userId, int productId,String token);
	ResponseEntity<String> deleteFromCart(int userId, int productId,String token);
	ResponseEntity<?> getProductDetails(int userId, int productId, String token);
	ResponseEntity<String> initiateOrder(int userId, String token);
	ResponseEntity<String> orderPayment(int userId,int orderId, String token, Payments payments);
	ResponseEntity<?> getOrderDetails(int userId, int orderId, String token);
	ResponseEntity<?> getOrdersDetails(int userId, String token);
}
