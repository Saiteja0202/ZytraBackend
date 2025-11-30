package com.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entities.Admin;


public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	Optional<Admin> findByAdminId(int adminId);
	Optional<Admin> findByAdminName(String adminName);
	Optional<Admin> findByEmail(String email);
	boolean existsByAdminName(String adminName);
    boolean existsByEmail(String email);

}
