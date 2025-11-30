package com.ecommerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.entities.Users;


public interface UsersService {
	
	ResponseEntity<String> userRegistration(Users users);
	ResponseEntity<?> userLogin(@RequestBody LoginDetails loginDetails);
	ResponseEntity<String> generateOtp(String email);
	ResponseEntity<String> userNameVerifyOtp(int userId,long otpNumber);
	ResponseEntity<String> passwordVerifyOtp(int userId,long otpNumber);
	ResponseEntity<String> updateForgotPassword(int userId,String newPassword);
	ResponseEntity<String> updateUserProfile(int userId,Users users);
	ResponseEntity<String> updatePassword(int userId,UpdatePasswordDetails updatePasswordDetails);
	
}
