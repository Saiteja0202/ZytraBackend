package com.ecommerce.serviceimplemnetation;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.AllProducts;
import com.ecommerce.entities.Brands;
import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Categories;
import com.ecommerce.entities.Discounts;
import com.ecommerce.entities.Inventory;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderItems;
import com.ecommerce.entities.Payments;
import com.ecommerce.entities.Products;
import com.ecommerce.entities.Reviews;
import com.ecommerce.entities.Seller;
import com.ecommerce.entities.Shipping;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.entities.Users;
import com.ecommerce.enums.MemberShipStatus;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.enums.PaymentType;
import com.ecommerce.enums.SellerStatus;
import com.ecommerce.enums.ShippingStatus;
import com.ecommerce.repository.BrandsRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CategoriesRepository;
import com.ecommerce.repository.DiscountsRepository;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.OrderItemsRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentsRepository;
import com.ecommerce.repository.ProductsRepository;
import com.ecommerce.repository.ReviewsRepository;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.repository.ShippingRepository;
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
	private final OrderRepository orderRepository;
	private final PaymentsRepository paymentsRepository;
	private final ShippingRepository shippingRepository;
	private final OrderItemsRepository orderItemsRepository;

	public OrderServiceImplementation(CategoriesRepository categoriesRepository,
			SubCategoryRepository subCategoryRepository, BrandsRepository brandsRepository,
			SellerRepository sellerRepository, DiscountsRepository discountsRepository,
			ProductsRepository productsRepository, InventoryRepository inventoryRepository,
			UsersRepository usersRepository, JwtUtil jwtUtil, CartRepository cartRepository,
			ReviewsRepository reviewsRepository,OrderRepository orderRepository,
			PaymentsRepository paymentsRepository,ShippingRepository shippingRepository, 
			OrderItemsRepository orderItemsRepository) {
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
		this.orderRepository=orderRepository;
		this.paymentsRepository=paymentsRepository;
		this.shippingRepository=shippingRepository;
		this.orderItemsRepository=orderItemsRepository;
	}

	@Override
	public ResponseEntity<String> addToCart(int userId, int productId, String token) {
	  
	    Users user = usersRepository.findByUserId(userId).orElse(null);
	    if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

	 
	    if (!jwtUtil.validateToken(token)) return ResponseEntity.status(401).body("Invalid or expired token");
	    Long tokenUserId = jwtUtil.extractUserId(token);
	    if (tokenUserId == null || tokenUserId != userId) return ResponseEntity.status(403).body("You are not authorized!");

	    Products product = productsRepository.findById(productId).orElse(null);
	    if (product == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");

	    Inventory inventory = inventoryRepository.findById(productId).orElse(null);
	    if (inventory == null || inventory.getStockQuantity() <= 0) return ResponseEntity.badRequest().body("Product out of stock");

	    Seller seller = sellerRepository.findById(product.getSellerId()).orElse(null);
	    if (seller == null || seller.getSellerStatusEnum() != SellerStatus.ACTIVE) return ResponseEntity.badRequest().body("Seller inactive");

	 
	    Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);

	    if (cart != null) {
	   
	        if (cart.getProductQuantity() < inventory.getStockQuantity()) {
	            cart.setProductQuantity(cart.getProductQuantity() + 1);
	            cartRepository.save(cart);
	            return ResponseEntity.ok("Product quantity updated in cart");
	        } else {
	            return ResponseEntity.badRequest().body("Not enough stock to add more");
	        }
	    }


	    Cart newCart = new Cart();
	    newCart.setUserId(userId);
	    newCart.setProductId(productId);
	    newCart.setProductQuantity(1);


	    newCart.setProductName(product.getProductName());
	    newCart.setActualPrice(product.getActualPrice());
	    newCart.setBrandName(brandsRepository.findById(product.getBrandId()).map(Brands::getBrandName).orElse(""));
	    newCart.setCategoryName(categoriesRepository.findById(product.getCategoryId()).map(Categories::getCategoryName).orElse(""));
	    newCart.setSubCategoryName(subCategoryRepository.findById(product.getSubCategoryId()).map(SubCategory::getSubCategoryName).orElse(""));
	    newCart.setSellerName(seller.getSellerName());
	    newCart.setColor(product.getColor());
	    newCart.setSize(product.getSize());
	    newCart.setImage(product.getImage());
	    newCart.setProductDescription(product.getProductDescription());
	    Discounts discount = discountsRepository.findById(product.getDiscountId()).orElse(null);
	    long totalPrice = product.getActualPrice();

	    if (discount != null) {
	        switch (discount.getDiscountType()) {
	            case "AMOUNT":
	                totalPrice -= discount.getDiscountValue();
	                break;
	            case "PERCENTAGE":
	                totalPrice -= (totalPrice * discount.getDiscountValue()) / 100;
	                break;
	            case "FLAT":
	                totalPrice = discount.getDiscountValue();
	                break;
	            default:
	                break;
	        }
	    }

	    if (user.getMemberShipStatus().equals(MemberShipStatus.PRIME)) {
	        totalPrice -= (totalPrice * 5) / 100; 
	    }

	    newCart.setTotalPrice(totalPrice);
	    newCart.setDiscountType(discount != null ? discount.getDiscountTypeEnum() : null);
	    newCart.setDiscountValue(discount != null ? discount.getDiscountValue() : 0);
	    newCart.setStartDate(discount != null ? discount.getStartDate() : null);
	    newCart.setEndDate(discount != null ? discount.getEndDate() : null);
	    newCart.setStockQuantity(inventory.getStockQuantity());

	    cartRepository.save(newCart);
	    return ResponseEntity.ok("Product added to cart successfully");
	}


	@Override
	public ResponseEntity<String> deleteFromCart(int userId, int productId, String token) {
	    Users user = usersRepository.findByUserId(userId).orElse(null);
	    if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

	    if (!jwtUtil.validateToken(token)) return ResponseEntity.status(401).body("Invalid or expired token");
	    Long tokenUserId = jwtUtil.extractUserId(token);
	    if (tokenUserId == null || tokenUserId != userId) return ResponseEntity.status(403).body("You are not authorized!");

	    Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);
	    if (cart == null) return ResponseEntity.badRequest().body("Product not in cart");

	    if (cart.getProductQuantity() > 1) {
	        cart.setProductQuantity(cart.getProductQuantity() - 1);
	        cartRepository.save(cart);
	        return ResponseEntity.ok("Product quantity decreased in cart");
	    } else {
	        cartRepository.delete(cart);
	        return ResponseEntity.ok("Product removed from cart");
	    }
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
			newProduct.setProductSubDescription(product.getProductSubDescription());
			newProduct.setImageFrontView(product.getImageFrontView());
			newProduct.setImageBottomView(product.getImageBottomView());
			newProduct.setImageSideView(product.getImageSideView());
			newProduct.setImageTopView(product.getImageTopView());
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
	
	@Override
	public ResponseEntity<String> initiateOrder(int userId, String token)
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
		
		List<Cart> allCarts =  cartRepository.findByUserId(userId);
		
		for(Cart cart : allCarts)
		{
			if(cart.getStockQuantity() <= 0)
			{
				return ResponseEntity.badRequest().body(cart.getProductName() + " is unavailabe");
			}
		}
		Order order = new Order();
		order.setOrderStatus(OrderStatus.INITIATED);
		order.setUserId(userId);
		order.setAddress(user.getDoorNumber()+", "+user.getStreet()+", "+user.getVillage()+", "+user.getLandMark()+", "
				+user.getCity()+", "+user.getDistrict()+", "+user.getPostalCode()+", "+user.getDistrict()+", "+user.getState()+
				", "+user.getCountry());
		order.setCarts(allCarts);
		orderRepository.save(order);
		return ResponseEntity.ok("Initiated Order : "+order.getOrderId());
	}
	
	
	@Override
	public ResponseEntity<String> orderPayment(int userId, int orderId, String token, Payments payments) {

	    Users user = usersRepository.findByUserId(userId).orElse(null);
	    Order order = orderRepository.findById(orderId).orElse(null);

	    if (user == null)
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

	    if (!jwtUtil.validateToken(token))
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");

	    Long tokenUserId = jwtUtil.extractUserId(token);
	    if (tokenUserId == null || tokenUserId != userId)
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");

	    Payments orderPayments = paymentsRepository.findByOrderId(orderId).orElse(null);

	    /* ===================== PAY LATER FLOW ===================== */
	    if (orderPayments != null &&
	        orderPayments.getPaymentStatus() == PaymentStatus.PENDING &&
	        orderPayments.getPaymentType() == PaymentType.PAYONDELIVERY &&
	        (payments.getPaymentType() == PaymentType.CARD ||
	         payments.getPaymentType() == PaymentType.UPI ||
	         payments.getPaymentType() == PaymentType.CASH)) {

	        // Update Payments table
	        orderPayments.setPaymentType(payments.getPaymentType());
	        orderPayments.setPaymentStatus(PaymentStatus.PAID);
	        orderPayments.setTransactionId(generateTransactionId());
	        paymentsRepository.save(orderPayments);

	        // 🔥 UPDATE ORDER ITEMS PAYMENT DETAILS
	        List<OrderItems> orderItems =
	                orderItemsRepository.findByOrderId(orderId);

	        for (OrderItems oi : orderItems) {
	            oi.setPaymentStatus(PaymentStatus.PAID);
	            oi.setPaymentType(payments.getPaymentType());
	            orderItemsRepository.save(oi);
	        }

	        return ResponseEntity.ok("Payment completed successfully");
	    }


	    /* ===================== INITIAL ORDER PLACEMENT ===================== */

	    List<Cart> allCarts = cartRepository.findByUserId(userId);
	    if (allCarts.isEmpty())
	        return ResponseEntity.badRequest().body("Cart is empty");

	    if (orderPayments == null) {
	        orderPayments = new Payments();
	        orderPayments.setOrderId(orderId);
	        orderPayments.setPaymentType(payments.getPaymentType());
	    }

	    PaymentStatus finalStatus =
	            payments.getPaymentType() == PaymentType.PAYONDELIVERY
	                    ? PaymentStatus.PENDING
	                    : PaymentStatus.PAID;

	    orderPayments.setPaymentStatus(finalStatus);
	    orderPayments.setTransactionId(generateTransactionId());
	    paymentsRepository.save(orderPayments);

	    Shipping shipping = shippingRepository.findByOrderId(orderId).orElse(null);
	    if (shipping == null) {
	        shipping = new Shipping();
	        shipping.setOrderId(orderId);
	        shipping.setShippingStatus(ShippingStatus.PROCESSING);
	        shipping.setAddress(order.getAddress());
	        shipping.setTrackingNumber(generateTransactionId());
	        shipping.setArrivingDate(
	                user.getMemberShipStatus() == MemberShipStatus.PRIME
	                        ? LocalDate.now().plusDays(2)
	                        : LocalDate.now().plusDays(7)
	        );
	        shippingRepository.save(shipping);
	    }

	    for (Cart cart : allCarts) {

	        Inventory inventory = inventoryRepository
	                .findById(cart.getProductId())
	                .orElseThrow(() -> new RuntimeException("Product unavailable"));

	        if (cart.getProductQuantity() > inventory.getStockQuantity())
	            return ResponseEntity.badRequest().body("Insufficient stock");

	        inventory.setStockQuantity(
	                inventory.getStockQuantity() - cart.getProductQuantity());
	        inventoryRepository.save(inventory);

	        saveOrderItemFromCart(userId, cart, order, shipping, orderPayments);
	    }

	    cartRepository.deleteAll(allCarts);

	    updateOrder(userId, orderId,
	            orderPayments.getPaymentId(),
	            shipping.getShippingId());

	    return ResponseEntity.ok(
	            finalStatus == PaymentStatus.PENDING
	                    ? "Order placed. Pay on delivery"
	                    : "Payment successful. Order placed"
	    );
	}



	private void saveOrderItemFromCart(int userId, Cart cart, Order order, Shipping shipping, Payments payments) {
	    OrderItems oi = new OrderItems();
	    oi.setUserId(userId);
	    oi.setCartId(cart.getCartId());
	    oi.setProductId(cart.getProductId());
	    oi.setBrandName(cart.getBrandName());
	    oi.setCategoryName(cart.getCategoryName());
	    oi.setColor(cart.getColor());
	    oi.setTotalPrice(cart.getTotalPrice());
	    oi.setImage(cart.getImage());
	    oi.setProductDescription(cart.getProductDescription());
	    oi.setProductName(cart.getProductName());
	    oi.setSellerName(cart.getSellerName());
	    oi.setSize(cart.getSize());
	    oi.setSubCategoryName(cart.getSubCategoryName());
	    oi.setStockQuantity(cart.getStockQuantity());
	    oi.setProductQuantity(cart.getProductQuantity());
	    oi.setOrderStatus(OrderStatus.CONFIRMED);
	    oi.setShippingStatus(shipping.getShippingStatus());
	    oi.setPaymentStatus(payments.getPaymentStatus());
	    oi.setPaymentType(payments.getPaymentType());
	    oi.setOrderDate(LocalDate.now());
	    oi.setOrderId(order.getOrderId());
	    orderItemsRepository.save(oi);
	}



	private void updateOrder(int userId, int orderId, int paymentId, int shippingId) {
	    Order order = orderRepository.findById(orderId).orElse(null);
	    Payments orderPayments = paymentsRepository.findByOrderId(orderId).orElse(null);

	    if (order == null || orderPayments == null) {
	        return;
	    }

	    if (orderPayments.getPaymentStatus().equals(PaymentStatus.PAID)
	            || orderPayments.getPaymentStatus().equals(PaymentStatus.PENDING)) {

	        order.setOrderDate(LocalDate.now());
	        order.setPaymentId(paymentId);
	        order.setShippingId(shippingId);
	        order.setOrderStatus(OrderStatus.CONFIRMED);
	        orderRepository.save(order);
	    }
	}

	private String generateTransactionId() {
	    String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(10);
	    for (int i = 0; i < 10; i++) {
	        int index = random.nextInt(charPool.length());
	        sb.append(charPool.charAt(index));
	    }
	    return sb.toString();
	}

	
	@Override
	public ResponseEntity<?> getOrderDetails(int userId, int orderId, String token) {
	    Users user = usersRepository.findByUserId(userId).orElse(null);

	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    if (!jwtUtil.validateToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
	    }

	    Long tokenUserId = jwtUtil.extractUserId(token);
	    if (tokenUserId == null || tokenUserId != userId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized !");
	    }

	    List<OrderItems> allOrderItems = orderItemsRepository.findByUserId(userId);


	    Map<LocalDate, List<OrderItems>> groupedByDate = allOrderItems.stream()
	            .collect(Collectors.groupingBy(OrderItems::getOrderDate));

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Map.Entry<LocalDate, List<OrderItems>> entry : groupedByDate.entrySet()) {
	        LocalDate date = entry.getKey();
	        List<OrderItems> items = entry.getValue();

	        Map<String, Object> orderGroup = new HashMap<>();
	        orderGroup.put("orderDate", date);

	        List<OrderItems> onlinePayments = items.stream()
	                .filter(i -> i.getPaymentType() == PaymentType.CARD || i.getPaymentType() == PaymentType.UPI)
	                .collect(Collectors.toList());

	        List<OrderItems> codPayments = items.stream()
	                .filter(i -> i.getPaymentType() == PaymentType.PAYONDELIVERY)
	                .collect(Collectors.toList());

	        List<Map<String, Object>> typeGroups = new ArrayList<>();


	        if (!onlinePayments.isEmpty()) {
	            Map<PaymentStatus, List<OrderItems>> groupedByStatus = onlinePayments.stream()
	                    .collect(Collectors.groupingBy(OrderItems::getPaymentStatus));

	            List<Map<String, Object>> statusGroups = new ArrayList<>();
	            for (Map.Entry<PaymentStatus, List<OrderItems>> statusEntry : groupedByStatus.entrySet()) {
	                PaymentStatus status = statusEntry.getKey();
	                List<OrderItems> statusItems = statusEntry.getValue();

	                long totalForStatus = statusItems.stream()
	                        .mapToLong(item -> item.getTotalPrice() * item.getProductQuantity())
	                        .sum();

	                Map<String, Object> statusGroup = new HashMap<>();
	                statusGroup.put("paymentStatus", status);
	                statusGroup.put("orderItems", statusItems);
	                statusGroup.put("totalPrice", totalForStatus);

	                statusGroups.add(statusGroup);
	            }

	            Map<String, Object> onlineGroup = new HashMap<>();
	            onlineGroup.put("paymentTypeGroup", "ONLINE (CARD/UPI)");
	            onlineGroup.put("groupsByPaymentStatus", statusGroups);
	            typeGroups.add(onlineGroup);
	        }


	        if (!codPayments.isEmpty()) {
	            Map<PaymentStatus, List<OrderItems>> groupedByStatus = codPayments.stream()
	                    .collect(Collectors.groupingBy(OrderItems::getPaymentStatus));

	            List<Map<String, Object>> statusGroups = new ArrayList<>();
	            for (Map.Entry<PaymentStatus, List<OrderItems>> statusEntry : groupedByStatus.entrySet()) {
	                PaymentStatus status = statusEntry.getKey();
	                List<OrderItems> statusItems = statusEntry.getValue();

	                long totalForStatus = statusItems.stream()
	                        .mapToLong(item -> item.getTotalPrice() * item.getProductQuantity())
	                        .sum();

	                Map<String, Object> statusGroup = new HashMap<>();
	                statusGroup.put("paymentStatus", status);
	                statusGroup.put("orderItems", statusItems);
	                statusGroup.put("totalPrice", totalForStatus);

	                statusGroups.add(statusGroup);
	            }

	            Map<String, Object> codGroup = new HashMap<>();
	            codGroup.put("paymentTypeGroup", "PAYONDELIVERY");
	            codGroup.put("groupsByPaymentStatus", statusGroups);
	            typeGroups.add(codGroup);
	        }

	        orderGroup.put("groupsByPaymentType", typeGroups);
	        responseList.add(orderGroup);
	    }

	    return ResponseEntity.ok(responseList);
	}



	
	@Scheduled(cron="0 0 0 * * *")
	public void updateShippingStatus()
	{
		List<Shipping> listOfAllShippingDetails = shippingRepository.findAll();
		for(Shipping shipping : listOfAllShippingDetails)
		{
			if(shipping.getShippingStatus().equals(ShippingStatus.PROCESSING))
			{
				Order order = orderRepository.findById(shipping.getOrderId()).orElse(null);
				long dayDiffer = ChronoUnit.DAYS.between(order.getOrderDate(), shipping.getArrivingDate());
				if( dayDiffer == 2)
				{
						long deliveryDaysDifferPrime = ChronoUnit.DAYS.between(LocalDate.now(), shipping.getArrivingDate());
						if(deliveryDaysDifferPrime == 1)
						{
							shipping.setShippingStatus(ShippingStatus.OUT_FOR_DELIVERY);
							shippingRepository.save(shipping);
						}
						if(deliveryDaysDifferPrime == 0)
						{
							shipping.setShippingStatus(ShippingStatus.DELIVERED);
							shippingRepository.save(shipping);
						}
				}
				if(dayDiffer == 7)
				{
					long deliveryDaysDifferNormal = ChronoUnit.DAYS.between(LocalDate.now(), shipping.getArrivingDate());
					if(deliveryDaysDifferNormal >=1 || deliveryDaysDifferNormal <= 6)
					{
						shipping.setShippingStatus(ShippingStatus.OUT_FOR_DELIVERY);
						shippingRepository.save(shipping);
					}
					if(deliveryDaysDifferNormal == 0)
					{
						shipping.setShippingStatus(ShippingStatus.DELIVERED);
						shippingRepository.save(shipping);
					}
				}
				
			}
		}
	}
	
	@Override
	public ResponseEntity<?> getOrdersDetails(int userId, String token) {
	    Users user = usersRepository.findByUserId(userId).orElse(null);

	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    if (!jwtUtil.validateToken(token)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
	    }

	    Long tokenUserId = jwtUtil.extractUserId(token);
	    if (tokenUserId == null || tokenUserId != userId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized!");
	    }
	 
	    List<OrderItems> allOrderItems = orderItemsRepository.findByUserId(userId);

	    Map<Integer, List<OrderItems>> groupedByOrder = allOrderItems.stream()
	            .collect(Collectors.groupingBy(OrderItems::getOrderId));

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Map.Entry<Integer, List<OrderItems>> entry : groupedByOrder.entrySet()) {
	        Integer orderId = entry.getKey();
	        List<OrderItems> items = entry.getValue();

	        Map<String, Object> orderMap = new HashMap<>();
	        orderMap.put("orderId", orderId);

	        // Use orderDate from the first item (all items in the same order should have same date)
	        LocalDate orderDate = items.isEmpty() ? null : items.get(0).getOrderDate();
	        orderMap.put("orderDate", orderDate);

	        // Group items by payment type
	        List<Map<String, Object>> typeGroups = new ArrayList<>();
	        Map<PaymentType, List<OrderItems>> itemsByPaymentType = items.stream()
	                .collect(Collectors.groupingBy(OrderItems::getPaymentType));

	        for (Map.Entry<PaymentType, List<OrderItems>> typeEntry : itemsByPaymentType.entrySet()) {
	            PaymentType paymentType = typeEntry.getKey();
	            List<OrderItems> typeItems = typeEntry.getValue();

	            Map<String, Object> typeGroup = new HashMap<>();
	            typeGroup.put("paymentType", paymentType);

	            // Group by payment status
	            Map<PaymentStatus, List<OrderItems>> itemsByStatus = typeItems.stream()
	                    .collect(Collectors.groupingBy(OrderItems::getPaymentStatus));

	            List<Map<String, Object>> statusGroups = new ArrayList<>();
	            for (Map.Entry<PaymentStatus, List<OrderItems>> statusEntry : itemsByStatus.entrySet()) {
	                PaymentStatus paymentStatus = statusEntry.getKey();
	                List<OrderItems> statusItems = statusEntry.getValue();

	                long totalPrice = statusItems.stream()
	                        .mapToLong(i -> i.getTotalPrice() * i.getProductQuantity())
	                        .sum();

	                Map<String, Object> statusMap = new HashMap<>();
	                statusMap.put("paymentStatus", paymentStatus);
	                statusMap.put("orderItems", statusItems);
	                statusMap.put("totalPrice", totalPrice);

	                statusGroups.add(statusMap);
	            }

	            typeGroup.put("groupsByPaymentStatus", statusGroups);
	            typeGroups.add(typeGroup);
	        }

	        orderMap.put("groupsByPaymentType", typeGroups);
	        responseList.add(orderMap);
	    }

	    return ResponseEntity.ok(responseList);
	}



}
