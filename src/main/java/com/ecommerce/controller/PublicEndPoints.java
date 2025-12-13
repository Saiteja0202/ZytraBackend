package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.PublicEndPointsService;

@RestController
public class PublicEndPoints {
	
	private final PublicEndPointsService publicEndPointsService;
	
	public PublicEndPoints(PublicEndPointsService publicEndPointsService)
	{
		this.publicEndPointsService=publicEndPointsService;
	}
	
	@GetMapping("/all-products")
	public ResponseEntity<?> getAllProductsCommon()
	{
		return publicEndPointsService.getAllProductsCommon();
	}
	
	@GetMapping("/all-categories")
	public ResponseEntity<?> getAllCategories()
	{
		return publicEndPointsService.getAllCategories();
	}
	
	@GetMapping("/all-sub-categories")
	public ResponseEntity<?> getAllSubCategories()
	{
		return publicEndPointsService.getAllSubCategories();
	}
	
	@GetMapping("/all-brands")
	public ResponseEntity<?> getAllBrands()
	{
		return publicEndPointsService.getAllBrands();
	}
	
	@GetMapping("/all-discounts")
	public ResponseEntity<?> getAllDiscounts()
	{
		return publicEndPointsService.getAllDiscounts();
	}
	
	
}
