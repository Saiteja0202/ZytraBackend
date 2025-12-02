package com.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

	boolean existsByCategoryName(String categoryName);
	
}
