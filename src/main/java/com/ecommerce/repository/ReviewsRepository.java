package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

	
	List<Reviews> findByProductId(int productId);
	
	
}
