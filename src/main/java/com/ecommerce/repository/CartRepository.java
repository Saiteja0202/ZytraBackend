package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	List<Cart> findByProductId(int productId);
	
	boolean existsByProductId(int productId);
	
	boolean existsByUserId(int userId);
	
	List<Cart> findByUserId(int userId);

	Cart findByUserIdAndProductId(int userId, int productId);
	
}
