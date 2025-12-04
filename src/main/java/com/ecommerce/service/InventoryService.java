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

	ResponseEntity<String> addNewCategory(int adminId, Categories categories,String token);

	ResponseEntity<String> addNewSubCategory(int adminId, SubCategory subCategory,String token);

	ResponseEntity<String> addNewBrand(int adminId, Brands brands,String token);

	ResponseEntity<?> getAllCategories(int adminId,String token);

	ResponseEntity<?> getAllSubCategories(int adminId,String token);
	
	ResponseEntity<?> getAllBrands(int adminId,String token);
	
	ResponseEntity<String> addNewSeller(int adminId,Seller seller,String token);
	
	ResponseEntity<?> getAllSellers(int adminId,String token);
	
	ResponseEntity<String> addNewDiscount(int adminId, Discounts discounts,String token);
	
	ResponseEntity<?> getAllDiscounts(int adminId,String token);
	
	ResponseEntity<String> addNewProduct(int adminId, Products products,String token);
	
	ResponseEntity<?> getAllProducts(int adminId,String token);
	
	ResponseEntity<String> activateSeller(int adminId, int sellerId,String token);
	
	ResponseEntity<String> deactivateSeller(int adminId, int sellerId,String token);
	
	ResponseEntity<String> updateProductDetails(int adminId, int productId,Products products,String token);
	
	ResponseEntity<String> addProductInInventroy(int adminId, Inventory inventory,String token);
	
	ResponseEntity<?> getAllInventory(int adminId,String token);
	
	ResponseEntity<String> updateInventory(int adminId, int inventoryId, Inventory inventory,String token);

}
