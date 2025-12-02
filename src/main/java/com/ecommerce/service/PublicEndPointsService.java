package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface PublicEndPointsService {
	
	ResponseEntity<?> getAllProductsCommon();

}
