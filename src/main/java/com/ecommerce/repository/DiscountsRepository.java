package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Discounts;

public interface DiscountsRepository extends JpaRepository<Discounts, Integer> {
	
	

}
