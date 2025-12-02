package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	
	boolean existsByProductName(String productName);
	List<Products> findByProductName(String productName);

}
