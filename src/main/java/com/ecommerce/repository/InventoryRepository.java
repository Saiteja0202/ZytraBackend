package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
	
	boolean existsByProductId(int productId);
	
	boolean existsBySellerId(int sellerId);
	
	Optional<Inventory> findByProductId(int productId);

}
