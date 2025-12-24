package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
	
	boolean existsByCartId(int cartId);
	
	List<OrderItems> findByUserId(int userId);

	List<OrderItems> findByOrderId(int orderId);
	
	boolean existsByUserIdAndProductId(int userId, int productId);


}
