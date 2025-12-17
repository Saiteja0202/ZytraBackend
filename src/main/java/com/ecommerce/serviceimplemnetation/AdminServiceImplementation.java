package com.ecommerce.serviceimplemnetation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.LoginResponse;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.dtos.UsersRegistrationHistory;
import com.ecommerce.entities.Admin;
import com.ecommerce.entities.Users;
import com.ecommerce.enums.AdminActivityStatus;
import com.ecommerce.repository.AdminRepository;
import com.ecommerce.repository.UsersRepository;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.AdminService;

@Service
public class AdminServiceImplementation implements AdminService {

	private final AdminRepository adminRepository;
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;


	public AdminServiceImplementation(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			 UsersRepository usersRepository) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.usersRepository = usersRepository;
	}

	@Override
	public ResponseEntity<String> adminRegistration(Admin newAdmin) {

		if (adminRepository.existsByAdminName(newAdmin.getAdminName())) {
			return ResponseEntity.badRequest().body("Adminname already exists");
		}
		if (adminRepository.existsByEmail(newAdmin.getEmail())) {
			return ResponseEntity.badRequest().body("Email already exists");
		}

		newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
		adminRepository.save(newAdmin);

		return ResponseEntity.ok("Admin registered successfully");

	}

	private int admin_attempts = 3;

	@Override
	public ResponseEntity<?> adminLogin(LoginDetails loginDetails) {

		if (loginDetails.getUserName() == null || loginDetails.getPassword() == null) {
			return ResponseEntity.badRequest().body("Enter Login Credentails !");
		}

		Admin newAdmin = adminRepository.findByAdminName(loginDetails.getUserName()).orElse(null);

		if (newAdmin == null) {
			return ResponseEntity.notFound().build();
		}
		if (newAdmin.getAdminActivityStatus() == "BLOCKED") {
			return ResponseEntity.status(403).body("Admin is blocked");
		}

		if (!passwordEncoder.matches(loginDetails.getPassword(), newAdmin.getPassword())) {
			admin_attempts--;
			if (admin_attempts <= 0 || newAdmin.getAdminActivityStatus().equals("BLOCKED")) {
				return ResponseEntity.status(400).body("Invalid credentials Admin Blocked");
			}
			return ResponseEntity.status(400).body("Invalid credentials \n Attempts Remaining : " + admin_attempts);
		}

		if (admin_attempts <= 0) {
			newAdmin.setAdminActivityStatus(AdminActivityStatus.BLOCKED);
			return ResponseEntity.status(400).body("Admin Blocked");
		}

		long adminId = newAdmin.getAdminId();
		String token = jwtUtil.generateToken(newAdmin.getEmail(), adminId, newAdmin.getUserRole());

		LoginResponse response = new LoginResponse(newAdmin.getAdminId(), newAdmin.getUserRole(), token,
				newAdmin.getEmail());
		admin_attempts = 3;
		return ResponseEntity.ok(response);

	}

	@Override
	public ResponseEntity<String> updateProfile(int adminId, Admin admin, String token) {

		Admin existingAdmin = adminRepository.findByAdminId(adminId).orElse(null);

		if (existingAdmin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		}
		
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != adminId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}


		existingAdmin.setFirstName(admin.getFirstName());
		existingAdmin.setLastName(admin.getLastName());
		existingAdmin.setPhoneNumber(admin.getPhoneNumber());
		existingAdmin.setEmail(admin.getEmail());
		existingAdmin.setAddress(admin.getAddress());
		existingAdmin.setCity(admin.getCity());
		existingAdmin.setDistrict(admin.getDistrict());
		existingAdmin.setPostalCode(admin.getPostalCode());
		existingAdmin.setState(admin.getState());
		existingAdmin.setCountry(admin.getCountry());
		existingAdmin.setAdminName(admin.getAdminName());

		adminRepository.save(existingAdmin);

		return ResponseEntity.ok("Profile updated successfully");
	}

	@Override
	public ResponseEntity<String> updatePassword(int adminId, UpdatePasswordDetails updatePasswordDetails, String token) {

		Admin admin = adminRepository.findByAdminId(adminId).orElse(null);
		if (admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		}
		
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != adminId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}

		
		if (updatePasswordDetails.getOldPassword() == null || updatePasswordDetails.getNewPassword() == null) {
			return ResponseEntity.badRequest().body("Enter Valid Crendentails !");
		}

		if (!passwordEncoder.matches(updatePasswordDetails.getOldPassword(), admin.getPassword())) {
			return ResponseEntity.badRequest().body("Invalid Crendentails !");
		}

		admin.setPassword(passwordEncoder.encode(updatePasswordDetails.getNewPassword()));
		adminRepository.save(admin);

		return ResponseEntity.ok("Password Updated Successfully");
	}
	
	
	@Override
	public ResponseEntity<?> getAdminDetails(int adminId, String token)
	{
		Admin admin = adminRepository.findByAdminId(adminId).orElse(null);
		
		if(admin == null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		}
		
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != adminId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}


		Admin adminDetails = new Admin();
		adminDetails.setAdminId(admin.getAdminId());
		adminDetails.setAdminName(admin.getAdminName());
		adminDetails.setFirstName(admin.getFirstName());
		adminDetails.setLastName(admin.getLastName());
		adminDetails.setPhoneNumber(admin.getPhoneNumber());
		adminDetails.setEmail(admin.getEmail());
		adminDetails.setAddress(admin.getAddress());
		adminDetails.setCity(admin.getCity());
		adminDetails.setDistrict(admin.getDistrict());
		adminDetails.setPostalCode(admin.getPostalCode());
		adminDetails.setState(admin.getState());
		adminDetails.setCountry(admin.getCountry());
	
		return ResponseEntity.ok(adminDetails);
	}

	@Override
	public ResponseEntity<?> getAllUserDetails(int adminId, String token) {
		
		Admin admin = adminRepository.findByAdminId(adminId).orElse(null);
		
		if(admin == null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		}
		
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		Long tokenUserId = jwtUtil.extractUserId(token);
		if (tokenUserId == null || tokenUserId != adminId) {
			return ResponseEntity.status(403).body("You are not authorized !");
		}


		List<Users> allUsers = usersRepository.findAll();

		ArrayList<UsersRegistrationHistory> allUsersDetailsList = new ArrayList<>();

		for (Users user : allUsers) {
			UsersRegistrationHistory newUser = new UsersRegistrationHistory();
			newUser.setUserId(user.getUserId());
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setPhoneNumber(user.getPhoneNumber());
			newUser.setEmail(user.getEmail());
			newUser.setRole(user.getRole());
			newUser.setAddress(user.getDoorNumber() + " " + user.getStreet() + " " + user.getVillage() + " "
					+ user.getLandMark() + " " + user.getCity() + " " + user.getDistrict() + " " + user.getPostalCode()
					+ " " + user.getState() + " " + user.getCountry());
			newUser.setMemberShipStatus(user.getMemberShipStatus());
			newUser.setRegisteredAt(user.getRegisteredDate());

			allUsersDetailsList.add(newUser);
		}

		return ResponseEntity.ok(allUsersDetailsList);
	}
	
	

}
