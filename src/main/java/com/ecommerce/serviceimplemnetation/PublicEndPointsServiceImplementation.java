package com.ecommerce.serviceimplemnetation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.AllProducts;
import com.ecommerce.dtos.SubCategoryDetails;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Reviews;
import com.ecommerce.entities.Seller;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.enums.SellerStatus;
import com.ecommerce.repository.BrandsRepository;
import com.ecommerce.repository.CategoriesRepository;
import com.ecommerce.repository.DiscountsRepository;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.ProductsRepository;
import com.ecommerce.repository.ReviewsRepository;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.service.PublicEndPointsService;

@Service
public class PublicEndPointsServiceImplementation implements PublicEndPointsService {

	private final CategoriesRepository categoriesRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final BrandsRepository brandsRepository;
	private final SellerRepository sellerRepository;
	private final DiscountsRepository discountsRepository;
	private final ProductsRepository productsRepository;
	private final InventoryRepository inventoryRepository;
	private final ReviewsRepository reviewsRepository;

	public PublicEndPointsServiceImplementation(CategoriesRepository categoriesRepository,
			SubCategoryRepository subCategoryRepository, BrandsRepository brandsRepository,
			SellerRepository sellerRepository, DiscountsRepository discountsRepository,
			ProductsRepository productsRepository, InventoryRepository inventoryRepository,
			ReviewsRepository reviewsRepository) {
		this.categoriesRepository = categoriesRepository;
		this.subCategoryRepository = subCategoryRepository;
		this.brandsRepository = brandsRepository;
		this.sellerRepository = sellerRepository;
		this.discountsRepository = discountsRepository;
		this.productsRepository = productsRepository;
		this.inventoryRepository = inventoryRepository;
		this.reviewsRepository=reviewsRepository;
	}

	@Override
	public ResponseEntity<?> getAllProductsCommon() {
		List<Products> allProducts = productsRepository.findAll();

		ArrayList<AllProducts> listOfAllProducts = new ArrayList<>();

		for (Products product : allProducts) {
			Inventory inventory = inventoryRepository.findByProductId(product.getProductId()).orElse(null);
			Seller seller = sellerRepository.findById(product.getSellerId()).orElse(null);
			SubCategory subCategory = subCategoryRepository.findById(product.getSubCategoryId()).orElse(null);
			Categories categories = categoriesRepository.findById(product.getCategoryId()).orElse(null);
			Brands brand = brandsRepository.findById(product.getBrandId()).orElse(null);
			Discounts discount = discountsRepository.findById(product.getDiscountId()).orElse(null);
			List<Reviews> allReviews = reviewsRepository.findByProductId(product.getProductId());
			if (seller.getSellerStatusEnum().equals(SellerStatus.ACTIVE) || seller.getSellerStatusEnum().equals(SellerStatus.INACTIVE)) {
				AllProducts newProduct = new AllProducts();
				newProduct.setProductId(product.getProductId());
				newProduct.setActualPrice(product.getActualPrice());
				newProduct.setBrandName(brand.getBrandName());
				newProduct.setCategoryName(categories.getCategoryName());
				newProduct.setColor(product.getColor());
				newProduct.setImage(product.getImage());
				newProduct.setProductName(product.getProductName());
				newProduct.setProductDescription(product.getProductDescription());
				newProduct.setSellerName(seller.getSellerName());
				newProduct.setSize(product.getSize());
				newProduct.setSubCategoryName(subCategory.getSubCategoryName());
				newProduct.setProductSubDescription(product.getProductSubDescription());
				newProduct.setImageFrontView(product.getImageFrontView());
				newProduct.setImageBottomView(product.getImageBottomView());
				newProduct.setImageSideView(product.getImageSideView());
				newProduct.setImageTopView(product.getImageTopView());
				String discountType = discount.getDiscountType();
				switch (discountType) {
				case "AMOUNT": {
					int amount = discount.getDiscountValue();
					newProduct.setTotalPrice(product.getActualPrice() - amount);
					break;
				}
				case "PERCENTAGE": {
					int percentage = discount.getDiscountValue();
					long actualPrice = product.getActualPrice();
					long discountAmount = (actualPrice * percentage) / 100;
					newProduct.setTotalPrice(actualPrice - discountAmount);
					break;
				}
				case "FLAT": {
					int flatAmount = discount.getDiscountValue();
					newProduct.setTotalPrice(flatAmount);
					break;
				}
				default:
					newProduct.setTotalPrice(product.getActualPrice());
				}
				
				if(allReviews == null)
				{
					newProduct.setAllReviews(null);
				}
				newProduct.setAllReviews(allReviews);
				newProduct.setDiscountType(discount.getDiscountTypeEnum());
				newProduct.setDiscountValue(discount.getDiscountValue());
				newProduct.setStartDate(discount.getStartDate());
				newProduct.setEndDate(discount.getEndDate());
				newProduct.setStockQuantity(inventory.getStockQuantity());
				newProduct.setSellerStatus(seller.getSellerStatusEnum());
				listOfAllProducts.add(newProduct);
			}
		}

		return ResponseEntity.ok(listOfAllProducts);
	}
	
	@Override
	public ResponseEntity<?> getAllCategories()
	{
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
			categories.setCategoryImage(category.getCategoryImage());
			listOfCategories.add(categories);
		}

		return ResponseEntity.ok(listOfCategories);
	}
	
	@Override
	public ResponseEntity<?> getAllSubCategories()
	{
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
	public ResponseEntity<?> getAllBrands()
	{
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
			brand.setBrandImage(brands.getBrandImage());
			listOfAllBrands.add(brand);
		}

		return ResponseEntity.ok(listOfAllBrands);
	}
	
	@Override
	public ResponseEntity<?> getAllDiscounts()
	{
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
	

}
