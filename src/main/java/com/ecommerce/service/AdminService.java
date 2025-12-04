package com.ecommerce.service;

import org.springframework.http.ResponseEntity;
import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.entities.Admin;

public interface AdminService {
	
	ResponseEntity<String> adminRegistration(Admin newAdmin);
	ResponseEntity<?> adminLogin(LoginDetails loginDetails);
	ResponseEntity<String> updateProfile(int adminId,Admin admin,String token);
	ResponseEntity<String> updatePassword(int adminId,UpdatePasswordDetails updatePasswordDetails,String token);
	ResponseEntity<?> getAllUserDetails(int adminId,String token);
}
