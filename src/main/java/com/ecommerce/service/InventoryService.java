package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.entities.Categories;
import com.ecommerce.entities.SubCategory;

public interface InventoryService {
	
	ResponseEntity<String> addNewCategory(int adminId, Categories categories);
	ResponseEntity<String> addNewSubCategory(int adminId, SubCategory subCategory);
	ResponseEntity<?> getAllCategories(int adminId);

}
