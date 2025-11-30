package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.dtos.UsersRegistrationHistory;
import com.ecommerce.entities.Admin;

public interface AdminService {
	
	ResponseEntity<String> adminRegistration(Admin newAdmin);
	ResponseEntity<?> adminLogin(LoginDetails loginDetails);
	ResponseEntity<String> updateProfile(int adminId,Admin admin);
	ResponseEntity<String> updatePassword(int adminId,UpdatePasswordDetails updatePasswordDetails);
	ResponseEntity<List<UsersRegistrationHistory>> getAllUserDetails(int adminId);
}
