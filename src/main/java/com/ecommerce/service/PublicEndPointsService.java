package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface PublicEndPointsService {
	
	ResponseEntity<?> getAllProductsCommon();
	ResponseEntity<?> getAllCategories();
	ResponseEntity<?> getAllSubCategories();
	ResponseEntity<?> getAllBrands();
	ResponseEntity<?> getAllDiscounts();
	
}
