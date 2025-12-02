package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Brands;

public interface BrandsRepository extends JpaRepository<Brands, Integer> {

	boolean existsByBrandName(String brandName);
	
}
