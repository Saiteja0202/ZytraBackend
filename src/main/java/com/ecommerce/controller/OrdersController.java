package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/user")
public class OrdersController {
	
	private final OrderService orderService;
	
	public OrdersController(OrderService orderService)
	{
		this.orderService=orderService;
	}
	
	
	@PostMapping("/add-to-cart/{userId}/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable int userId, @PathVariable int productId,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return orderService.addToCart(userId,productId,token);
	}

	@DeleteMapping("/delete-from-cart/{userId}/{productId}")
	public ResponseEntity<String> deleteFromCart(@PathVariable int userId, @PathVariable int productId,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return orderService.deleteFromCart(userId, productId, token);
	}
	
	@GetMapping("/get-product/{userId}/{productId}")
	public ResponseEntity<?> getProductDetails(@PathVariable int userId, @PathVariable int productId,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return orderService.getProductDetails(userId, productId,token);
	}
	
	
}

