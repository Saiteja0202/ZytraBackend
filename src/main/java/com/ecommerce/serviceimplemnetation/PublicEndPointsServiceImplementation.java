package com.ecommerce.serviceimplemnetation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.AllProducts;
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

	public PublicEndPointsServiceImplementation(CategoriesRepository categoriesRepository,
			SubCategoryRepository subCategoryRepository, BrandsRepository brandsRepository,
			SellerRepository sellerRepository, DiscountsRepository discountsRepository,
			ProductsRepository productsRepository, InventoryRepository inventoryRepository) {
		this.categoriesRepository = categoriesRepository;
		this.subCategoryRepository = subCategoryRepository;
		this.brandsRepository = brandsRepository;
		this.sellerRepository = sellerRepository;
		this.discountsRepository = discountsRepository;
		this.productsRepository = productsRepository;
		this.inventoryRepository = inventoryRepository;
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
			Brands brand = brandsRepository.findById(product.getProductId()).orElse(null);
			Discounts discount = discountsRepository.findById(product.getDiscountId()).orElse(null);
			if (inventory.getStockQuantity() > 0 || seller.getSellerStatusEnum().equals(SellerStatus.ACTIVE)) {
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

}
