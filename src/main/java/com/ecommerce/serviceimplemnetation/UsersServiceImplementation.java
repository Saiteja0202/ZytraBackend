package com.ecommerce.serviceimplemnetation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.AllProducts;
import com.ecommerce.dtos.CartResponse;
import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.LoginResponse;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.OrderItems;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Reviews;
import com.ecommerce.entities.Seller;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.entities.Users;
import com.ecommerce.enums.MemberShipStatus;
import com.ecommerce.enums.OtpStatus;
import com.ecommerce.enums.SellerStatus;
import com.ecommerce.repository.BrandsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CategoriesRepository;
import com.ecommerce.repository.DiscountsRepository;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.OrderItemsRepository;
import com.ecommerce.repository.ProductsRepository;
import com.ecommerce.repository.ReviewsRepository;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.repository.UsersRepository;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.UsersService;

@Service
public class UsersServiceImplementation implements UsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final JavaMailSender javaMailSender;
	private final CategoriesRepository categoriesRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final BrandsRepository brandsRepository;
	private final SellerRepository sellerRepository;
	private final DiscountsRepository discountsRepository;
	private final ProductsRepository productsRepository;
	private final InventoryRepository inventoryRepository;
	private final ReviewsRepository reviewsRepository;
	private final CartRepository cartRepository;
	private final OrderItemsRepository orderItemsRepository;

	public UsersServiceImplementation(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
			SubCategoryRepository subCategoryRepository, JwtUtil jwtUtil, JavaMailSender javaMailSender,
			CategoriesRepository categoriesRepository, BrandsRepository brandsRepository,
			SellerRepository sellerRepository, DiscountsRepository discountsRepository,
			ProductsRepository productsRepository, InventoryRepository inventoryRepository,
			ReviewsRepository reviewsRepository,CartRepository cartRepository,OrderItemsRepository orderItemsRepository) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.javaMailSender = javaMailSender;
		this.categoriesRepository = categoriesRepository;
		this.subCategoryRepository = subCategoryRepository;
		this.brandsRepository = brandsRepository;
		this.sellerRepository = sellerRepository;
		this.discountsRepository = discountsRepository;
		this.productsRepository = productsRepository;
		this.inventoryRepository = inventoryRepository;
		this.reviewsRepository = reviewsRepository;
		this.cartRepository=cartRepository;
		this.orderItemsRepository=orderItemsRepository;
	}

	@Override
	public ResponseEntity<String> userRegistration(Users newUser) {

		if (usersRepository.existsByUserName(newUser.getUserName())) {
			return ResponseEntity.badRequest().body("User Name is already exists !");
		}
		if (usersRepository.existsByEmail(newUser.getEmail())) {
			return ResponseEntity.badRequest().body("Email is already exists !");
		}

		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setRegisteredDate(LocalDate.now());
		usersRepository.save(newUser);

//		SimpleMailMessage message = new SimpleMailMessage();
//		  message.setTo(newUser.getEmail());
//		    message.setSubject("Welcome to OurApp - Registration Successful");
//		    message.setText(
//		        "Dear " + newUser.getFirstName() +" " +newUser.getLastName() + ",\n\n" +
//		        "Thank you for registering with OurApp!\n\n" +
//		        "Your account has been successfully created on " + LocalDate.now() + ".\n" +
//		        "Your user name : "+newUser.getUserName()+".\n\n"+
//		        "You can now log in and start shopping.\n\n" +
//		        "If you have any questions, feel free to contact our support team.\n\n" +
//		        "Best regards,\n" +
//		        "OurApp Customer Support"
//		    );    
//		    javaMailSender.send(message);

		return ResponseEntity.ok("Registration Successful");
	}

	@Override
	public ResponseEntity<?> userLogin(LoginDetails loginDetails) {

		if (loginDetails.getUserName() == null || loginDetails.getPassword() == null) {
			return ResponseEntity.badRequest().body("Enter Login Credentails !");
		}

		Users newUser = usersRepository.findByUserName(loginDetails.getUserName()).orElse(null);

		if (newUser == null) {
			return ResponseEntity.badRequest().body("User Name Not Found!");
		}

		if (!passwordEncoder.matches(loginDetails.getPassword(), newUser.getPassword())) {
			return ResponseEntity.badRequest().body("Invalid Cretendials !");
		}

		long userId = newUser.getUserId();
		String token = jwtUtil.generateToken(newUser.getEmail(), userId, newUser.getUserRole());

		LoginResponse response = new LoginResponse(newUser.getUserId(), newUser.getUserRole(), token,
				newUser.getEmail());

		return ResponseEntity.ok(response);

	}

	@Override
	public ResponseEntity<String> generateOtp(String email) {

		Users newUser = usersRepository.findByEmail(email).orElse(null);

		if (newUser == null) {
			return ResponseEntity.badRequest().body("Email not exists or found !");
		}

		if (!newUser.getOtpStatus().equals("GENERATE")) {
			return ResponseEntity.badRequest().body("Otp already sent to mail, please check !");
		}

		Random random = new Random();
		long otp = 1000L + random.nextInt(9000);
		newUser.setOtpNumber(otp);
		newUser.setOtpStatus(OtpStatus.PENDING);
		newUser.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		usersRepository.save(newUser);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your One-Time Password (OTP) for Secure Login");
		message.setText("Dear Customer,\n\n"
				+ "Thank you for using OurApp. To complete your login, please use the following One-Time Password (OTP):\n\n"
				+ "OTP: " + otp + "\n\n" + "This OTP is valid for the next 5 minutes. Do not share it with anyone.\n\n"
				+ "If you did not request this, please ignore this email or contact our support team.\n\n"
				+ "Best regards,\n" + "OurApp Customer Support");
		javaMailSender.send(message);

		return ResponseEntity.ok("Otp generated, check mail " + newUser.getUserId());
	}

	@Override
	public ResponseEntity<String> userNameVerifyOtp(int userId, long enteredOtp) {
		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
		}

		if (user.getOtpNumber() == 0L || !user.getOtpStatus().equals("PENDING")) {
			return ResponseEntity.badRequest().body("No active OTP found!");
		}

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			user.setOtpStatus(OtpStatus.GENERATE);
			usersRepository.save(user);
			return ResponseEntity.badRequest().body("OTP expired!");
		}

		if (user.getOtpNumber() != enteredOtp) {
			return ResponseEntity.badRequest().body("Invalid OTP!");
		}

		user.setOtpStatus(OtpStatus.GENERATE);
		user.setOtpNumber(0L);
		usersRepository.save(user);

		return ResponseEntity.ok(user.getUserName());
	}

	@Override
	public ResponseEntity<String> passwordVerifyOtp(int userId, long enteredOtp) {
		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
		}

		if (user.getOtpNumber() == 0L || !user.getOtpStatus().equals("PENDING")) {
			return ResponseEntity.badRequest().body("No active OTP found!");
		}

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			user.setOtpStatus(OtpStatus.GENERATE);
			usersRepository.save(user);
			return ResponseEntity.badRequest().body("OTP expired!");
		}

		if (user.getOtpNumber() != enteredOtp) {
			return ResponseEntity.badRequest().body("Invalid OTP!");
		}

		user.setOtpStatus(OtpStatus.VERIFIED);
		user.setOtpNumber(0L);
		usersRepository.save(user);

		return ResponseEntity.ok("Otp Verified !");

	}

	public ResponseEntity<String> updateForgotPassword(int userId, String newPassword) {
		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		if (!user.getOtpStatus().equals("VERIFIED")) {
			return ResponseEntity.badRequest().body("OTP not verified");
		}

		user.setOtpStatus(OtpStatus.PENDING);
		user.setPassword(passwordEncoder.encode(newPassword));
		usersRepository.save(user);

		return ResponseEntity.ok("Successfully updated new password");

	}

	@Override
	public ResponseEntity<String> updateUserProfile(int userId, Users users, String token) {
		Users existingUser = usersRepository.findByUserId(userId).orElse(null);

		if (existingUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != userId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}

		existingUser.setFirstName(users.getFirstName());
		existingUser.setLastName(users.getLastName());
		existingUser.setPhoneNumber(users.getPhoneNumber());
		existingUser.setEmail(users.getEmail());
		existingUser.setDoorNumber(users.getDoorNumber());
		existingUser.setStreet(users.getStreet());
		existingUser.setVillage(users.getVillage());
		existingUser.setLandMark(users.getLandMark());
		existingUser.setCity(users.getCity());
		existingUser.setDistrict(users.getDistrict());
		existingUser.setPostalCode(users.getPostalCode());
		existingUser.setState(users.getState());
		existingUser.setCountry(users.getCountry());
		existingUser.setUserName(users.getUserName());
		usersRepository.save(existingUser);

		return ResponseEntity.ok("Profile updated successfully");
	}

	@Override
	public ResponseEntity<String> updatePassword(int userId, UpdatePasswordDetails updatePasswordDetails,
			String token) {
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

		if (updatePasswordDetails.getOldPassword() == null || updatePasswordDetails.getNewPassword() == null) {
			return ResponseEntity.badRequest().body("Enter Valid Crendentails !");
		}

		if (!passwordEncoder.matches(updatePasswordDetails.getOldPassword(), user.getPassword())) {
			return ResponseEntity.badRequest().body("Invalid Crendentails !");
		}

		user.setPassword(passwordEncoder.encode(updatePasswordDetails.getNewPassword()));
		usersRepository.save(user);

		return ResponseEntity.ok("Password Updated Successfully");
	}

	@Override
	public ResponseEntity<?> getAllProducts(int userId, String token) {

		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != userId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}

		List<Products> allProducts = productsRepository.findAll();

		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		ArrayList<AllProducts> listOfAllProducts = new ArrayList<>();

		for (Products product : allProducts) {
			Inventory inventory = inventoryRepository.findByProductId(product.getProductId()).orElse(null);
			Seller seller = sellerRepository.findById(product.getSellerId()).orElse(null);
			SubCategory subCategory = subCategoryRepository.findById(product.getSubCategoryId()).orElse(null);
			Categories categories = categoriesRepository.findById(product.getCategoryId()).orElse(null);
			Brands brand = brandsRepository.findById(product.getBrandId()).orElse(null);
			Discounts discount = discountsRepository.findById(product.getDiscountId()).orElse(null);
			List<Reviews> allReviews = reviewsRepository.findByProductId(product.getProductId());
			if (seller.getSellerStatusEnum().equals(SellerStatus.ACTIVE)) {
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
				if (allReviews == null) {
					newProduct.setAllReviews(null);
				}
				newProduct.setAllReviews(allReviews);

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

				newProduct.setDiscountType(discount.getDiscountTypeEnum());
				newProduct.setDiscountValue(discount.getDiscountValue());
				newProduct.setStartDate(discount.getStartDate());
				newProduct.setEndDate(discount.getEndDate());
				newProduct.setStockQuantity(inventory.getStockQuantity());
				newProduct.setSellerStatus(seller.getSellerStatusEnum());
				newProduct.setImageFrontView(product.getImageFrontView());
				newProduct.setImageTopView(product.getImageTopView());
				newProduct.setImageBottomView(product.getImageBottomView());
				newProduct.setImageSideView(product.getImageSideView());
				newProduct.setProductSubDescription(product.getProductSubDescription());
				listOfAllProducts.add(newProduct);
			}
		}

		return ResponseEntity.ok(listOfAllProducts);

	}

	@Override
	public ResponseEntity<String> subscribeToPrime(int userId, String token) {

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

		if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
			return ResponseEntity.badRequest().body("Already Subscribed to Prime");
		}

		user.setMemberShipStatus(MemberShipStatus.PRIME);
		user.setMemberShipExpiryDate(LocalDate.now().plusMonths(1));
		usersRepository.save(user);

		return ResponseEntity.ok("Successfully subscribed to prime");
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void unSubscribe() {
		List<Users> allUsers = usersRepository.findAll();

		for (Users user : allUsers) {
			if (user.getMemberShipExpiryDate().equals(LocalDate.now())) {
				user.setMemberShipExpiryDate(null);
				user.setMemberShipStatus(MemberShipStatus.NORMAL);
				usersRepository.save(user);
			}
		}
	}

	@Override
	public ResponseEntity<String> reviewProduct(int userId, int productId, String token, Reviews reviews) {

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

		List<Reviews> allReviews = reviewsRepository.findByProductId(productId);

		for (Reviews review : allReviews) {
			if (review.getUserId() == userId) {
				return ResponseEntity.badRequest().body("Already Reviewed");
			}
		}

		boolean hasPurchased =
			    orderItemsRepository.existsByUserIdAndProductId(userId, productId);

			if (!hasPurchased) {
			    return ResponseEntity
			        .badRequest()
			        .body("You must purchase this product to review it");
			}

		
		reviews.setUserId(userId);
		reviews.setProductId(productId);
		reviews.setCreatedAt(LocalDate.now());
		reviewsRepository.save(reviews);

		return ResponseEntity.ok("Reviewd successfully");
	}

	@Override
	public ResponseEntity<String> updateReview(int userId, int productId, String token, Reviews reviews) {

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
		
		List<Reviews> allReviews = reviewsRepository.findByProductId(productId);
		
		if(allReviews.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reviews not found");
		}
		boolean hasPurchased =
			    orderItemsRepository.existsByUserIdAndProductId(userId, productId);

			if (!hasPurchased) {
			    return ResponseEntity
			        .badRequest()
			        .body("You cannot update this review");
			}

		
		for(Reviews review : allReviews)
		{
			if(review.getUserId() == userId)
			{
				review.setRating(reviews.getRating());
				review.setComment(reviews.getComment());
				review.setCreatedAt(LocalDate.now());
				reviewsRepository.save(review);
			}
		}

		return ResponseEntity.ok("Successfully updated the review");
	}
	
	
	@Override
	public ResponseEntity<?> getCart(int userId, String token)
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
		
		List<Cart> allCart = cartRepository.findByUserId(userId);
		
		if(allCart == null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
		}
		
		ArrayList<Cart> listOfCart = new ArrayList<>();
		
		long cartTotalPrice = 0L;
		
		for(Cart cart : allCart)
		{
			Cart newCart = new Cart();
			newCart.setCartId(cart.getCartId());
			newCart.setUserId(cart.getUserId());
			newCart.setActualPrice(cart.getActualPrice());
			newCart.setBrandName(cart.getBrandName());
			newCart.setCategoryName(cart.getCategoryName());
			newCart.setSize(cart.getSize());
			newCart.setColor(cart.getColor());
			newCart.setDiscountType(cart.getDiscountType());
			newCart.setDiscountValue(cart.getDiscountValue());
			newCart.setEndDate(cart.getEndDate());
			newCart.setImage(cart.getImage());
			newCart.setProductDescription(cart.getProductDescription());
			newCart.setProductId(cart.getProductId());
			newCart.setProductName(cart.getProductName());
			newCart.setProductQuantity(cart.getProductQuantity());
			newCart.setSellerName(cart.getSellerName());
			newCart.setStartDate(cart.getStartDate());
			newCart.setStockQuantity(cart.getStockQuantity());
			newCart.setSubCategoryName(cart.getSubCategoryName());
			newCart.setTotalPrice(cart.getTotalPrice());
			listOfCart.add(newCart);
			cartTotalPrice = cartTotalPrice + (newCart.getTotalPrice() * newCart.getProductQuantity());
		}
		
		CartResponse response = new CartResponse();
		response.setCarts(listOfCart);
		response.setTotalPrice(cartTotalPrice);
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<?> getUserDetails(int userId, String token)
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
		
		return ResponseEntity.ok(user);
	}

}
