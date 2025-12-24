package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.entities.Reviews;
import com.ecommerce.entities.Users;
import com.ecommerce.service.UsersService;

@RestController
@RequestMapping("/user")
public class UsersController {
	
	private final UsersService userService;
	
	public UsersController(UsersService userService)
	{
		this.userService = userService;
	}
	
	
	@PostMapping("/registration")
	public ResponseEntity<String> userRegistration(@RequestBody Users users)
	{
		return userService.userRegistration(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody LoginDetails loginDetails)
	{
		return userService.userLogin(loginDetails);
	}
	
	@PostMapping("/generate-otp")
	public ResponseEntity<String> generateOtp(@RequestBody Users users)
	{
		String email = users.getEmail();
		return userService.generateOtp(email);
	}
	
	@PostMapping("/forgot-username/verify-otp")
	public ResponseEntity<String> userNameVerifyOtp(@RequestBody Users users)
	{
		long otp = users.getOtpNumber();
		int userId = users.getUserId();
		return userService.userNameVerifyOtp(userId,otp);
	}
	
	@PostMapping("/forgot-password/verify-otp")
	public ResponseEntity<String> passwordVerifyOtp(@RequestBody Users users)
	{
		long otp = users.getOtpNumber();
		int userId = users.getUserId();
		return userService.passwordVerifyOtp(userId,otp);
	}
	
	@PostMapping("/update-forgot-password")
	public ResponseEntity<String> updateForgotPassword(@RequestBody UpdatePasswordDetails updatePasswordDetails)
	{
		int userId = updatePasswordDetails.getUserId();
		String newPassword = updatePasswordDetails.getNewPassword();
		return userService.updateForgotPassword(userId,newPassword);
	}
	
	@PutMapping("/update-profile/{userId}")
	public ResponseEntity<String> updateProfile(@PathVariable int userId, @RequestBody Users users,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.updateUserProfile(userId, users,token);
	}
	
	@PutMapping("/update-password/{userId}")
	public ResponseEntity<String> updatePassword(@PathVariable int userId, @RequestBody UpdatePasswordDetails updatePasswordDetails,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.updatePassword(userId,updatePasswordDetails,token);
	}
	
	@GetMapping("/all-products/{userId}")
	public ResponseEntity<?> getAllProducts(@PathVariable int userId, @RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.getAllProducts(userId,token);
	}
	
	@PutMapping("/subscribe-prime/{userId}")
	public ResponseEntity<String> subscribeToPrime(@PathVariable int userId, @RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.subscribeToPrime(userId, token);
	}
	
	@PostMapping("/review-product/{userId}/{productId}")
	public ResponseEntity<String> reviewProduct(@PathVariable int userId, @PathVariable int productId, @RequestBody Reviews reviews,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.reviewProduct(userId, productId,token,reviews);
	}
	
	@PutMapping("/update-review/{userId}/{productId}")
	public ResponseEntity<String> updateReview(@PathVariable int userId, @PathVariable int productId, @RequestBody Reviews reviews,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.updateReview(userId, productId,token,reviews);
	}
	
	@GetMapping("/get-cart/{userId}")
	public ResponseEntity<?> getCart(@PathVariable int userId, @RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.getCart(userId, token);
	}
	
	@GetMapping("/get-user-details/{userId}")
	public ResponseEntity<?> getUserDetails(@PathVariable int userId,@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return userService.getUserDetails(userId, token);
	}
	

}
