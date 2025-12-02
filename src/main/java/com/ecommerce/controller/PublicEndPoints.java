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

}
