package com.ecommerce.service;

import org.springframework.http.ResponseEntity;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Seller;
import com.ecommerce.entities.SubCategory;

public interface InventoryService {

	ResponseEntity<String> addNewCategory(int adminId, Categories categories);

	ResponseEntity<String> addNewSubCategory(int adminId, SubCategory subCategory);

	ResponseEntity<String> addNewBrand(int adminId, Brands brands);

	ResponseEntity<?> getAllCategories(int adminId);

	ResponseEntity<?> getAllSubCategories(int adminId);
	
	ResponseEntity<?> getAllBrands(int adminId);
	
	ResponseEntity<String> addNewSeller(int adminId,Seller seller);
	
	ResponseEntity<?> getAllSellers(int adminId);
	
	ResponseEntity<String> addNewDiscount(int adminId, Discounts discounts);
	
	ResponseEntity<?> getAllDiscounts(int adminId);
	
	ResponseEntity<String> addNewProduct(int adminId, Products products);
	
	ResponseEntity<?> getAllProducts(int adminId);
	
	ResponseEntity<String> activateSeller(int adminId, int sellerId);
	
	ResponseEntity<String> deactivateSeller(int adminId, int sellerId);
	
	ResponseEntity<String> updateProductDetails(int adminId, int productId,Products products);
	
	ResponseEntity<String> addProductInInventroy(int adminId, Inventory inventory);
	
	ResponseEntity<?> getAllInventory(int adminId);
	
	ResponseEntity<String> updateInventory(int adminId, int inventoryId, Inventory inventory);

}
