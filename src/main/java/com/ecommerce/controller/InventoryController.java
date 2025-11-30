package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entities.Categories;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.service.InventoryService;

@RestController
@RequestMapping("/admin/inventory")
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	public InventoryController(InventoryService inventoryService)
	{
		this.inventoryService=inventoryService;
	}
	
	@PostMapping("/add-category/{adminId}")
	public ResponseEntity<String> addNewCategory(@PathVariable int adminId, @RequestBody Categories categories)
	{
		return inventoryService.addNewCategory(adminId,categories);
	}
	
	@PostMapping("/add-subcategory/{adminId}")
	public ResponseEntity<String> addNewSubCategory(@PathVariable int adminId, @RequestBody SubCategory subCategory)
	{
		return inventoryService.addNewSubCategory(adminId,subCategory);
	}
	
	@GetMapping("/get-all-categories/{adminId}")
	public ResponseEntity<?> getAllCategories(@PathVariable int adminId)
	{
		return inventoryService.getAllCategories(adminId);
	}
	
	

}
