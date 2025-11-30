package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer>{
	
	boolean existsBySubCategoryName(String subCategoryName);

}
