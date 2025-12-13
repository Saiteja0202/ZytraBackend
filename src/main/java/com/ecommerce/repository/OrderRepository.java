package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
