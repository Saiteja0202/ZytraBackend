package com.ecommerce.serviceimplemnetation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.dtos.AllProducts;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Reviews;
import com.ecommerce.entities.Seller;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.entities.Users;
import com.ecommerce.enums.MemberShipStatus;
import com.ecommerce.enums.SellerStatus;
import com.ecommerce.repository.BrandsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CategoriesRepository;
import com.ecommerce.repository.DiscountsRepository;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.ProductsRepository;
import com.ecommerce.repository.ReviewsRepository;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.repository.UsersRepository;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.OrderService;

@Service
public class OrderServiceImplementation implements OrderService {

	private final CategoriesRepository categoriesRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final BrandsRepository brandsRepository;
	private final SellerRepository sellerRepository;
	private final DiscountsRepository discountsRepository;
	private final ProductsRepository productsRepository;
	private final InventoryRepository inventoryRepository;
	private final UsersRepository usersRepository;
	private final JwtUtil jwtUtil;
	private final CartRepository cartRepository;
	private final ReviewsRepository reviewsRepository;

	public OrderServiceImplementation(CategoriesRepository categoriesRepository,
			SubCategoryRepository subCategoryRepository, BrandsRepository brandsRepository,
			SellerRepository sellerRepository, DiscountsRepository discountsRepository,
			ProductsRepository productsRepository, InventoryRepository inventoryRepository,
			UsersRepository usersRepository, JwtUtil jwtUtil, CartRepository cartRepository,
			ReviewsRepository reviewsRepository) {
		this.categoriesRepository = categoriesRepository;
		this.subCategoryRepository = subCategoryRepository;
		this.brandsRepository = brandsRepository;
		this.sellerRepository = sellerRepository;
		this.discountsRepository = discountsRepository;
		this.productsRepository = productsRepository;
		this.inventoryRepository = inventoryRepository;
		this.usersRepository = usersRepository;
		this.jwtUtil = jwtUtil;
		this.cartRepository = cartRepository;
		this.reviewsRepository=reviewsRepository;
	}

	@Override
	public ResponseEntity<String> addToCart(int userId, int productId, String token) {

		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != userId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}

		Products product = productsRepository.findById(productId).orElse(null);

		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
		Inventory inventory = inventoryRepository.findById(productId).orElse(null);
		
		if (cartRepository.existsByProductId(productId)) {
			Cart cart = cartRepository.findByProductId(productId).orElse(null);
			if(inventory.getStockQuantity() > 1)
			{
				if (cart.getProductQuantity() >= 1) {
					cart.setProductQuantity(cart.getProductQuantity() + 1);
					cartRepository.save(cart);
				}
			}
		}

		else {
			Categories category = categoriesRepository.findById(product.getCategoryId()).orElse(null);
			SubCategory subCategory = subCategoryRepository.findById(product.getSubCategoryId()).orElse(null);
			
			Brands brand = brandsRepository.findById(product.getBrandId()).orElse(null);
			Seller seller = sellerRepository.findById(product.getSellerId()).orElse(null);
			Discounts discount = discountsRepository.findById(product.getDiscountId()).orElse(null);
			if (inventory.getStockQuantity() > 0 || seller.getSellerStatusEnum().equals(SellerStatus.ACTIVE)) {
				Cart newCart = new Cart();
				newCart.setProductId(product.getProductId());
				newCart.setActualPrice(product.getActualPrice());
				newCart.setBrandName(brand.getBrandName());
				newCart.setCategoryName(category.getCategoryName());
				newCart.setColor(product.getColor());
				newCart.setImage(product.getImage());
				newCart.setProductName(product.getProductName());
				newCart.setProductDescription(product.getProductDescription());
				newCart.setSellerName(seller.getSellerName());
				newCart.setSize(product.getSize());
				newCart.setSubCategoryName(subCategory.getSubCategoryName());
				String discountType = discount.getDiscountType();
				switch (discountType) {
				case "AMOUNT": {
					int amount = discount.getDiscountValue();
					if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
						long totalPrice = product.getActualPrice() - amount;
						long primeDiscountAmount = (totalPrice * 5) / 100;
						newCart.setTotalPrice(totalPrice - primeDiscountAmount);
					} else {
						newCart.setTotalPrice(product.getActualPrice() - amount);
					}

					break;
				}
				case "PERCENTAGE": {
					int percentage = discount.getDiscountValue();
					long actualPrice = product.getActualPrice();
					long discountAmount = (actualPrice * percentage) / 100;
					if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
						long totalPrice = actualPrice - discountAmount;
						long primeDiscountAmount = (totalPrice * 5) / 100;
						newCart.setTotalPrice(totalPrice - primeDiscountAmount);
					} else {
						newCart.setTotalPrice(actualPrice - discountAmount);
					}

					break;
				}
				case "FLAT": {
					int flatAmount = discount.getDiscountValue();

					if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
						long totalPrice = flatAmount;
						long primeDiscountAmount = (totalPrice * 5) / 100;
						newCart.setTotalPrice(totalPrice - primeDiscountAmount);
					} else {
						newCart.setTotalPrice(flatAmount);
					}
					break;
				}
				default:

					if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
						long totalPrice = product.getActualPrice();
						long primeDiscountAmount = (totalPrice * 5) / 100;
						newCart.setTotalPrice(totalPrice - primeDiscountAmount);
					} else {
						newCart.setTotalPrice(product.getActualPrice());
					}
				}

				newCart.setDiscountType(discount.getDiscountTypeEnum());
				newCart.setDiscountValue(discount.getDiscountValue());
				newCart.setStartDate(discount.getStartDate());
				newCart.setEndDate(discount.getEndDate());
				newCart.setStockQuantity(inventory.getStockQuantity());
				newCart.setUserId(userId);
				newCart.setProductId(productId);
				newCart.setProductQuantity(1);
				cartRepository.save(newCart);
			}
		}

		return ResponseEntity.ok("Successfully added to cart");
	}

	@Override
	public ResponseEntity<String> deleteFromCart(int userId, int productId, String token) {

		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != userId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}
		
		Cart cart = cartRepository.findByProductId(productId).orElse(null);
		
		if(cart == null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
		}
		
		if(cart.getProductQuantity() <= 0)
		{
			return ResponseEntity.badRequest().body("Unable to delete");
		}
		
		if(cart.getProductQuantity() == 1)
		{
			cartRepository.delete(cart);
		}
		else {
			if(cart.getProductQuantity() > 1)
			{
				cart.setProductQuantity(cart.getProductQuantity() - 1);
				cartRepository.save(cart);
			}
		}
		return ResponseEntity.ok("Successfully Deleted from cart");
	}
	
	@Override
	public ResponseEntity<?> getProductDetails(int userId, int productId, String token)
	{
		

		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != userId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}
		
		Products product = productsRepository.findById(productId).orElse(null);
		
		if(product == null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
		
		Categories category = categoriesRepository.findById(product.getCategoryId()).orElse(null);
		SubCategory subCategory = subCategoryRepository.findById(product.getSubCategoryId()).orElse(null);
		Inventory inventory = inventoryRepository.findById(productId).orElse(null);
		Brands brand = brandsRepository.findById(product.getBrandId()).orElse(null);
		Seller seller = sellerRepository.findById(product.getSellerId()).orElse(null);
		Discounts discount = discountsRepository.findById(product.getDiscountId()).orElse(null);
		List<Reviews> allReviews = reviewsRepository.findByProductId(productId); 
		AllProducts newProduct = new AllProducts();
		if (seller.getSellerStatusEnum().equals(SellerStatus.ACTIVE)) {
			
			newProduct.setProductId(product.getProductId());
			newProduct.setActualPrice(product.getActualPrice());
			newProduct.setBrandName(brand.getBrandName());
			newProduct.setCategoryName(category.getCategoryName());
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
				if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
					long totalPrice = product.getActualPrice() - amount;
					long primeDiscountAmount = (totalPrice * 5) / 100;
					newProduct.setTotalPrice(totalPrice - primeDiscountAmount);
				} else {
					newProduct.setTotalPrice(product.getActualPrice() - amount);
				}

				break;
			}
			case "PERCENTAGE": {
				int percentage = discount.getDiscountValue();
				long actualPrice = product.getActualPrice();
				long discountAmount = (actualPrice * percentage) / 100;
				if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
					long totalPrice = actualPrice - discountAmount;
					long primeDiscountAmount = (totalPrice * 5) / 100;
					newProduct.setTotalPrice(totalPrice - primeDiscountAmount);
				} else {
					newProduct.setTotalPrice(actualPrice - discountAmount);
				}

				break;
			}
			case "FLAT": {
				int flatAmount = discount.getDiscountValue();

				if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
					long totalPrice = flatAmount;
					long primeDiscountAmount = (totalPrice * 5) / 100;
					newProduct.setTotalPrice(totalPrice - primeDiscountAmount);
				} else {
					newProduct.setTotalPrice(flatAmount);
				}
				break;
			}
			default:

				if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
					long totalPrice = product.getActualPrice();
					long primeDiscountAmount = (totalPrice * 5) / 100;
					newProduct.setTotalPrice(totalPrice - primeDiscountAmount);
				} else {
					newProduct.setTotalPrice(product.getActualPrice());
				}
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
			newProduct.setProductId(productId);
			newProduct.setSellerStatus(seller.getSellerStatusEnum());
		}
		
		return ResponseEntity.ok(newProduct);
	}
	
	
	

}
