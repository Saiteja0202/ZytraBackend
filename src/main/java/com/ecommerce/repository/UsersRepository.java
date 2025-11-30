package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByUserId(int userId);
	Optional<Users> findByUserName(String userName);
	Optional<Users> findByEmail(String email);
	boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    
}
