package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByProductId(int productId);
	
	boolean existsByProductId(int productId);
	
	List<Cart> findByUserId(int userId);
	
}
