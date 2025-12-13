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
import com.ecommerce.entities.Admin;
import com.ecommerce.service.AdminService;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	
	public AdminController(AdminService adminService)
	{
		this.adminService=adminService;
	}
	
	@PostMapping("/registration")
	public ResponseEntity<String> adminRegistration(@RequestBody Admin admin)
	{
		return adminService.adminRegistration(admin);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> adminLogin(@RequestBody LoginDetails loginDetails)
	{
		return adminService.adminLogin(loginDetails);
	}
	
	@PutMapping("/update-profile/{adminId}")
	public ResponseEntity<String> updateProfile(@PathVariable int adminId,@RequestBody Admin admin,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return adminService.updateProfile(adminId, admin,token);
	}
	
	@PutMapping("/update-password/{adminId}")
	public ResponseEntity<String> updatePassword(@PathVariable int adminId, @RequestBody UpdatePasswordDetails updatePasswordDetails,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return adminService.updatePassword(adminId,updatePasswordDetails,token);
	}
	
	@GetMapping("/get-all-user-details/{adminId}")
	public ResponseEntity<?> getAllUserDetails(@PathVariable int adminId,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return adminService.getAllUserDetails(adminId,token);
	}
	
	@GetMapping("/get-admin-details/{adminId}")
	public ResponseEntity<?> getAdminDetails(@PathVariable int adminId,
			@RequestHeader("Authorization") String authHeader)
	{
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
		return adminService.getAdminDetails(adminId, token);
	}
	 

}
