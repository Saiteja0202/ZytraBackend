package com.ecommerce.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Seller;
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
	public ResponseEntity<String> addNewCategory(@PathVariable int adminId, @RequestBody Categories categories,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addNewCategory(adminId,categories, token);
	}
	
	@PostMapping("/add-subcategory/{adminId}")
	public ResponseEntity<String> addNewSubCategory(@PathVariable int adminId, @RequestBody SubCategory subCategory,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addNewSubCategory(adminId,subCategory,token);
	}
	
	@PostMapping("/add-brand/{adminId}")
	public ResponseEntity<String> addNewBrand(@PathVariable int adminId, @RequestBody Brands brands,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addNewBrand(adminId, brands,token);
	}
	
	@GetMapping("/get-all-categories/{adminId}")
	public ResponseEntity<?> getAllCategories(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllCategories(adminId,token);
	}
	
	@GetMapping("/get-all-subcategories/{adminId}")
	public ResponseEntity<?> getAllSubCategories(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllSubCategories(adminId,token);
	}
	
	@GetMapping("/get-all-brands/{adminId}")
	public ResponseEntity<?> getAllBrands(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllBrands(adminId,token);
	}
	
	@PostMapping("/add-seller/{adminId}")
	public ResponseEntity<String> addNewSeller(@PathVariable int adminId,@RequestBody Seller seller,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addNewSeller(adminId,seller,token);
	}
	
	@GetMapping("/get-all-sellers/{adminId}")
	public ResponseEntity<?> getAllSellers(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllSellers(adminId,token);
	}
	
	@PostMapping("/add-discount/{adminId}")
	public ResponseEntity<String> addNewDiscount(@PathVariable int adminId, @RequestBody Discounts discounts,@RequestHeader("Authorization") String authHeader)
	{ 
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addNewDiscount(adminId, discounts,token);
	}
	
	@GetMapping("/get-all-discounts/{adminId}")
	public ResponseEntity<?> getAllDiscounts(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllDiscounts(adminId,token);
	}
	
	@PostMapping("/add-new-product/{adminId}")
	public ResponseEntity<String> addNewProduct(@PathVariable int adminId, @RequestBody Products products,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addNewProduct(adminId,products,token);
	}
	
	@GetMapping("/get-all-products/{adminId}")
	public ResponseEntity<?> getAllProducts(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllProducts(adminId,token);
	}
	
	@PutMapping("/activate-seller/{adminId}/{sellerId}")
	public ResponseEntity<String> activateSeller(@PathVariable int adminId, @PathVariable int sellerId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.activateSeller(adminId, sellerId,token);
	}
	
	@PutMapping("/deactivate-seller/{adminId}/{sellerId}")
	public ResponseEntity<String> deactivateSeller(@PathVariable int adminId, @PathVariable int sellerId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.deactivateSeller(adminId, sellerId,token);
	}
	
	@PutMapping("/update-product-details/{adminId}/{productId}")
	public ResponseEntity<String> updateProductDetails(@PathVariable int adminId, @PathVariable int productId,
			@RequestBody Products products,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.updateProductDetails(adminId,productId, products,token);
	}
	
	@PostMapping("/add-inventory/{adminId}")
	public ResponseEntity<String> addProductInInventroy(@PathVariable int adminId, @RequestBody Inventory inventory,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.addProductInInventroy(adminId, inventory,token);
	}
	
	@GetMapping("/get-all-inventory/{adminId}")
	public ResponseEntity<?> getAllInventory(@PathVariable int adminId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.getAllInventory(adminId,token);
	}
	
	@PutMapping("/update-inventory/{adminId}/{inventoryId}")
	public ResponseEntity<String> updateInventory(@PathVariable int adminId ,@PathVariable int inventoryId, @RequestBody Inventory inventory,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return inventoryService.updateInventory(adminId,inventoryId,inventory,token);
	}
}
