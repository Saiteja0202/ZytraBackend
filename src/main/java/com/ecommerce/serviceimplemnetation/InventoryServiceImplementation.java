package com.ecommerce.serviceimplemnetation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.SubCategoryDetails;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Seller;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.enums.SellerStatus;
import com.ecommerce.repository.BrandsRepository;
import com.ecommerce.repository.CategoriesRepository;
import com.ecommerce.repository.DiscountsRepository;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.ProductsRepository;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.service.InventoryService;

@Service
public class InventoryServiceImplementation implements InventoryService {

	private final CategoriesRepository categoriesRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final BrandsRepository brandsRepository;
	private final SellerRepository sellerRepository;
	private final DiscountsRepository discountsRepository;
	private final ProductsRepository productsRepository;
	private final InventoryRepository inventoryRepository;

	public InventoryServiceImplementation(CategoriesRepository categoriesRepository,
			SubCategoryRepository subCategoryRepository, BrandsRepository brandsRepository,
			SellerRepository sellerRepository, DiscountsRepository discountsRepository,
			ProductsRepository productsRepository,InventoryRepository inventoryRepository) {
		this.categoriesRepository = categoriesRepository;
		this.subCategoryRepository = subCategoryRepository;
		this.brandsRepository = brandsRepository;
		this.sellerRepository = sellerRepository;
		this.discountsRepository = discountsRepository;
		this.productsRepository = productsRepository;
		this.inventoryRepository=inventoryRepository;
	}

	@Override
	public ResponseEntity<String> addNewCategory(int adminId, Categories categories) {

		if (categoriesRepository.existsByCategoryName(categories.getCategoryName())) {
			return ResponseEntity.badRequest().body("Category already exists");
		}

		categories.setCategoryName(categories.getCategoryName().toLowerCase());
		categoriesRepository.save(categories);

		return ResponseEntity.ok("Category added successfully");
	}

	@Override
	public ResponseEntity<String> addNewSubCategory(int adminId, SubCategory subCategory) {

		if (subCategoryRepository.existsBySubCategoryName(subCategory.getSubCategoryName())) {
			return ResponseEntity.badRequest().body("Subcategory already exists");
		}

		subCategory.setSubCategoryName(subCategory.getSubCategoryName().toLowerCase());
		subCategoryRepository.save(subCategory);

		return ResponseEntity.ok("Subcategory addded successfully");
	}

	@Override
	public ResponseEntity<?> getAllCategories(int adminId) {

		List<Categories> allCategories = categoriesRepository.findAll();

		if (allCategories == null) {
			return ResponseEntity.badRequest().body("Categories not found");
		}

		ArrayList<Categories> listOfCategories = new ArrayList<>();
		for (Categories category : allCategories) {
			Categories categories = new Categories();
			categories.setCategoryId(category.getCategoryId());
			categories.setCategoryName(category.getCategoryName());
			categories.setCategoryDescription(category.getCategoryDescription());
			listOfCategories.add(categories);
		}

		return ResponseEntity.ok(listOfCategories);
	}

	@Override
	public ResponseEntity<?> getAllSubCategories(int adminId) {

		List<SubCategory> allSubCategories = subCategoryRepository.findAll();

		if (allSubCategories == null) {
			return ResponseEntity.badRequest().body("SubCategories not found");
		}

		ArrayList<SubCategoryDetails> listOfAllSubCategories = new ArrayList<>();
		for (SubCategory subCategory : allSubCategories) {
			SubCategoryDetails subCategoryDetails = new SubCategoryDetails();
			subCategoryDetails.setSubCategoryId(subCategory.getSubCategoryId());
			subCategoryDetails.setSubCategoryName(subCategory.getSubCategoryName());
			subCategoryDetails.setSubCategoryDescription(subCategory.getSubCategoryDescription());
			Categories category = categoriesRepository.findById(subCategory.getCategoryId()).orElse(null);
			subCategoryDetails.setCategoryName(category.getCategoryName());
			subCategoryDetails.setCategoryDescription(category.getCategoryDescription());
			listOfAllSubCategories.add(subCategoryDetails);
		}

		return ResponseEntity.ok(listOfAllSubCategories);
	}

	@Override
	public ResponseEntity<String> addNewBrand(int adminId, Brands brands) {

		if (brandsRepository.existsByBrandName(brands.getBrandName())) {
			return ResponseEntity.badRequest().body("Brand already exists");
		}

		brands.setBrandName(brands.getBrandName().toLowerCase());

		brandsRepository.save(brands);

		return ResponseEntity.ok("Brand Added Successfully");
	}

	@Override
	public ResponseEntity<?> getAllBrands(int adminId) {

		List<Brands> allBrands = brandsRepository.findAll();

		if (allBrands == null) {
			return ResponseEntity.badRequest().body("Brands not found");
		}

		ArrayList<Brands> listOfAllBrands = new ArrayList<>();

		for (Brands brands : allBrands) {
			Brands brand = new Brands();
			brand.setBrandId(brands.getBrandId());
			brand.setBrandName(brands.getBrandName());
			brand.setBrandDescription(brands.getBrandDescription());
			listOfAllBrands.add(brand);
		}

		return ResponseEntity.ok(listOfAllBrands);
	}

	@Override
	public ResponseEntity<String> addNewSeller(int adminId, Seller seller) {

		if (sellerRepository.existsBySellerName(seller.getSellerName())) {
			return ResponseEntity.badRequest().body("Seller already exists");
		}

		if (sellerRepository.existsByEmail(seller.getEmail())) {
			return ResponseEntity.badRequest().body("Seller Email already exists");
		}

		if (sellerRepository.existsByPhoneNumber(seller.getPhoneNumber())) {
			return ResponseEntity.badRequest().body("Seller Phone number already exists");
		}

		seller.setAdminId(adminId);
		seller.setSellerStatus(SellerStatus.ACTIVE);
		seller.setCreatedAt(LocalDateTime.now());
		seller.setUpdatedAt(LocalDateTime.now());
		sellerRepository.save(seller);

		return ResponseEntity.ok("Seller Added Successfully");
	}

	@Override
	public ResponseEntity<?> getAllSellers(int adminId) {

		List<Seller> allSellers = sellerRepository.findAll();

		if (allSellers == null) {
			return ResponseEntity.badRequest().body("Sellers not found");
		}

		ArrayList<Seller> listOfAllSellers = new ArrayList<>();
		for (Seller seller : allSellers) {
			Seller newSeller = new Seller();
			newSeller.setSellerId(seller.getSellerId());
			newSeller.setSellerName(seller.getSellerName());
			newSeller.setAdminId(seller.getAdminId());
			newSeller.setEmail(seller.getEmail());
			newSeller.setPhoneNumber(seller.getPhoneNumber());
			newSeller.setAddress(seller.getAddress());
			newSeller.setSellerStatus(seller.getSellerStatusEnum());
			newSeller.setCreatedAt(seller.getCreatedAt());
			newSeller.setUpdatedAt(seller.getUpdatedAt());
			listOfAllSellers.add(newSeller);

		}

		return ResponseEntity.ok(listOfAllSellers);
	}

	@Override
	public ResponseEntity<String> addNewDiscount(int adminId, Discounts discounts) {

		discountsRepository.save(discounts);
		return ResponseEntity.ok("New Discount added successfully");
	}

	@Override
	public ResponseEntity<?> getAllDiscounts(int adminId) {

		List<Discounts> allDiscounts = discountsRepository.findAll();

		ArrayList<Discounts> listOfAllDiscounts = new ArrayList<>();

		for (Discounts discounts : allDiscounts) {

			Discounts discount = new Discounts();
			discount.setDiscountId(discounts.getDiscountId());
			discount.setDiscountType(discounts.getDiscountTypeEnum());
			discount.setDiscountValue(discounts.getDiscountValue());
			discount.setStartDate(discounts.getStartDate());
			discount.setEndDate(discounts.getEndDate());
			listOfAllDiscounts.add(discount);
		}

		return ResponseEntity.ok(listOfAllDiscounts);
	}

	@Override
	public ResponseEntity<String> addNewProduct(int adminId, Products products) {

		if (productsRepository.existsByProductName(products.getProductName())) {
			List<Products> allProducts = productsRepository.findByProductName(products.getProductName());
			for (Products product : allProducts) {
				if (product.getSellerId() == products.getSellerId()) {
					return ResponseEntity.badRequest()
							.body("This Product is already exists for the same seller! Can't add this product");
				}
			}
		}
		products.setAdminId(adminId);
		products.setCreatedAt(LocalDateTime.now());
		products.setUpdatedAt(LocalDateTime.now());
		productsRepository.save(products);
		return ResponseEntity.ok("Product added successfully");
	}

	@Override
	public ResponseEntity<?> getAllProducts(int adminId) {

		List<Products> allProducts = productsRepository.findAll();

		ArrayList<Products> listOfAllProducts = new ArrayList<>();

		for (Products products : allProducts) {
			Products product = new Products();
			product.setProductId(products.getProductId());
			product.setCategoryId(products.getCategoryId());
			product.setSubCategoryId(products.getSubCategoryId());
			product.setBrandId(products.getBrandId());
			product.setSellerId(products.getSellerId());
			product.setDiscountId(products.getDiscountId());
			product.setProductName(products.getProductName());
			product.setProductDescription(products.getProductDescription());
			product.setActualPrice(products.getActualPrice());
			product.setImage(products.getImage());
			product.setColor(products.getColor());
			product.setSize(products.getSize());
			product.setAdminId(adminId);
			product.setCreatedAt(products.getCreatedAt());
			listOfAllProducts.add(product);
		}
		return ResponseEntity.ok(listOfAllProducts);
	}

	@Override
	public ResponseEntity<String> activateSeller(int adminId, int sellerId) {

		Seller existingSeller = sellerRepository.findById(sellerId).orElse(null);

		if (existingSeller == null) {
			return ResponseEntity.badRequest().body("Seller not found");
		}

		if (existingSeller.getSellerStatusEnum().equals(SellerStatus.ACTIVE)) {
			return ResponseEntity.badRequest().body("Seller account is already in Active");
		}

		existingSeller.setSellerStatus(SellerStatus.ACTIVE);
		existingSeller.setUpdatedAt(LocalDateTime.now());
		sellerRepository.save(existingSeller);

		return ResponseEntity.ok("Successfully activated the seller account");
	}

	@Override
	public ResponseEntity<String> deactivateSeller(int adminId, int sellerId) {
		Seller existingSeller = sellerRepository.findById(sellerId).orElse(null);

		if (existingSeller == null) {
			return ResponseEntity.badRequest().body("Seller not found");
		}

		if (existingSeller.getSellerStatusEnum().equals(SellerStatus.INACTIVE)) {
			return ResponseEntity.badRequest().body("Seller account is already in InActive");
		}

		existingSeller.setSellerStatus(SellerStatus.INACTIVE);
		existingSeller.setUpdatedAt(LocalDateTime.now());
		sellerRepository.save(existingSeller);

		return ResponseEntity.ok("Successfully Deactivated the seller account");
	}

	@Override
	public ResponseEntity<String> updateProductDetails(int adminId, int productId, Products products) {

		Products existingProducts = productsRepository.findById(productId).orElse(null);

		if (existingProducts == null) {
			return ResponseEntity.badRequest().body("Product not found");
		}

		existingProducts.setActualPrice(products.getActualPrice());
		existingProducts.setAdminId(adminId);
		existingProducts.setBrandId(products.getBrandId());
		existingProducts.setCategoryId(products.getCategoryId());
		existingProducts.setColor(products.getColor());
		existingProducts.setUpdatedAt(LocalDateTime.now());
		existingProducts.setDiscountId(products.getDiscountId());
		existingProducts.setImage(products.getImage());
		existingProducts.setProductDescription(products.getProductDescription());
		existingProducts.setProductName(products.getProductName());
		existingProducts.setSellerId(products.getSellerId());
		existingProducts.setSize(products.getSize());
		existingProducts.setSubCategoryId(products.getSubCategoryId());

		productsRepository.save(existingProducts);

		return ResponseEntity.ok("Successfully updated the product details");
	}

	@Override
	public ResponseEntity<String> addProductInInventroy(int adminId, Inventory inventory) {
		
		if(inventoryRepository.existsByProductId(inventory.getProductId()))
		{
			if(inventoryRepository.existsBySellerId(inventory.getSellerId()))
			{
				return ResponseEntity.badRequest().body("This product is already exists for this seller");
			}
		}
		
		inventory.setLastUpdatedAt(LocalDateTime.now());
		inventoryRepository.save(inventory);
		
		return ResponseEntity.ok("Successfully added the inventory");
	}

	@Override
	public ResponseEntity<?> getAllInventory(int adminId) {
		
		List<Inventory> allInventories = inventoryRepository.findAll();
		
		ArrayList<Inventory> listOfAllInventories = new ArrayList<>();
		
		for(Inventory inventory : allInventories)
		{
			Inventory newInventory = new Inventory();
			newInventory.setInventoryId(inventory.getInventoryId());
			newInventory.setProductId(inventory.getProductId());
			newInventory.setStockQuantity(inventory.getStockQuantity());
			newInventory.setWareHouseLocation(inventory.getWareHouseLocation());
			newInventory.setLastUpdatedAt(inventory.getLastUpdatedAt());
			newInventory.setSellerId(inventory.getSellerId());
			listOfAllInventories.add(newInventory);
		}
		
		return ResponseEntity.ok(listOfAllInventories);
	}

	@Override
	public ResponseEntity<String> updateInventory(int adminId, int inventoryId, Inventory inventory) {
		
		Inventory existingInventory = inventoryRepository.findById(inventoryId).orElse(null);
		
		if(existingInventory == null)
		{
			return ResponseEntity.badRequest().body("Inventory not found");
		}
		
		existingInventory.setLastUpdatedAt(LocalDateTime.now());
		existingInventory.setProductId(inventory.getProductId());
		existingInventory.setSellerId(inventory.getSellerId());
		existingInventory.setStockQuantity(inventory.getStockQuantity());
		existingInventory.setWareHouseLocation(inventory.getWareHouseLocation());
		inventoryRepository.save(existingInventory);
		
		return ResponseEntity.ok("Successfully updated Inventory");
	}
	
	
	

}
