package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

	boolean existsBySellerName(String sellerName);
	boolean existsByEmail(String email);
	boolean existsByPhoneNumber(String phoneNumber);
	
}
