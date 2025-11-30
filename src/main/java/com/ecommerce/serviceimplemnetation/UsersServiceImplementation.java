package com.ecommerce.serviceimplemnetation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.dtos.LoginDetails;
import com.ecommerce.dtos.LoginResponse;
import com.ecommerce.dtos.UpdatePasswordDetails;
import com.ecommerce.entities.Users;
import com.ecommerce.enums.OtpStatus;
import com.ecommerce.repository.UsersRepository;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.UsersService;

@Service
public class UsersServiceImplementation implements UsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final JavaMailSender javaMailSender;

	public UsersServiceImplementation(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			JavaMailSender javaMailSender) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.javaMailSender = javaMailSender;
	}

	@Override
	public ResponseEntity<String> userRegistration(Users newUser) {

		if (usersRepository.existsByUserName(newUser.getUserName())) {
			return ResponseEntity.badRequest().body("User Name is already exists !");
		}
		if (usersRepository.existsByEmail(newUser.getEmail())) {
			return ResponseEntity.badRequest().body("Email is already exists !");
		}

		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setRegisteredDate(LocalDate.now());
		usersRepository.save(newUser);
		
		SimpleMailMessage message = new SimpleMailMessage();
		  message.setTo(newUser.getEmail());
		    message.setSubject("Welcome to OurApp - Registration Successful");
		    message.setText(
		        "Dear " + newUser.getFirstName() +" " +newUser.getLastName() + ",\n\n" +
		        "Thank you for registering with OurApp!\n\n" +
		        "Your account has been successfully created on " + LocalDate.now() + ".\n" +
		        "Your user name : "+newUser.getUserName()+".\n\n"+
		        "You can now log in and start shopping.\n\n" +
		        "If you have any questions, feel free to contact our support team.\n\n" +
		        "Best regards,\n" +
		        "OurApp Customer Support"
		    );
		    javaMailSender.send(message);

		
		return ResponseEntity.ok("Registration Successful");
	}

	@Override
	public ResponseEntity<?> userLogin(LoginDetails loginDetails) {

		if (loginDetails.getUserName() == null || loginDetails.getPassword() == null) {
			return ResponseEntity.badRequest().body("Enter Login Credentails !");
		}

		Users newUser = usersRepository.findByUserName(loginDetails.getUserName()).orElse(null);

		if (newUser == null) {
			return ResponseEntity.badRequest().body("User Name Not Found!");
		}

		if (!passwordEncoder.matches(loginDetails.getPassword(), newUser.getPassword())) {
			return ResponseEntity.badRequest().body("Invalid Cretendials !");
		}

		long userId = newUser.getUserId();
		String token = jwtUtil.generateToken(newUser.getEmail(), userId, newUser.getUserRole());

		LoginResponse response = new LoginResponse(newUser.getUserId(), newUser.getUserRole(), token,
				newUser.getEmail());

		return ResponseEntity.ok(response);

	}

	@Override
	public ResponseEntity<String> generateOtp(String email) {

		Users newUser = usersRepository.findByEmail(email).orElse(null);

		if (newUser == null) {
			return ResponseEntity.badRequest().body("Email not exists or found !");
		}

		if (!newUser.getOtpStatus().equals("GENERATE")) {
			return ResponseEntity.badRequest().body("Otp already sent to mail, please check !");
		}

		Random random = new Random();
		long otp = 1000L + random.nextInt(9000);
		newUser.setOtpNumber(otp);
		newUser.setOtpStatus(OtpStatus.PENDING);
		newUser.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		usersRepository.save(newUser);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your One-Time Password (OTP) for Secure Login");
		message.setText("Dear Customer,\n\n"
				+ "Thank you for using OurApp. To complete your login, please use the following One-Time Password (OTP):\n\n"
				+ "OTP: " + otp + "\n\n" + "This OTP is valid for the next 5 minutes. Do not share it with anyone.\n\n"
				+ "If you did not request this, please ignore this email or contact our support team.\n\n"
				+ "Best regards,\n" + "OurApp Customer Support");
		javaMailSender.send(message);

		return ResponseEntity.ok("Otp generated, check mail " + newUser.getUserId());
	}

	@Override
	public ResponseEntity<String> userNameVerifyOtp(int userId, long enteredOtp) {
		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.badRequest().body("Email not found!");
		}

		if (user.getOtpNumber() == 0L || !user.getOtpStatus().equals("PENDING")) {
			return ResponseEntity.badRequest().body("No active OTP found!");
		}

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			user.setOtpStatus(OtpStatus.GENERATE);
			usersRepository.save(user);
			return ResponseEntity.badRequest().body("OTP expired!");
		}

		if (user.getOtpNumber() != enteredOtp) {
			return ResponseEntity.badRequest().body("Invalid OTP!");
		}

		user.setOtpStatus(OtpStatus.GENERATE);
		user.setOtpNumber(0L);
		usersRepository.save(user);

		return ResponseEntity.ok(user.getUserName());
	}

	@Override
	public ResponseEntity<String> passwordVerifyOtp(int userId, long enteredOtp) {
		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.badRequest().body("Email not found!");
		}

		if (user.getOtpNumber() == 0L || !user.getOtpStatus().equals("PENDING")) {
			return ResponseEntity.badRequest().body("No active OTP found!");
		}

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			user.setOtpStatus(OtpStatus.GENERATE);
			usersRepository.save(user);
			return ResponseEntity.badRequest().body("OTP expired!");
		}

		if (user.getOtpNumber() != enteredOtp) {
			return ResponseEntity.badRequest().body("Invalid OTP!");
		}

		user.setOtpStatus(OtpStatus.VERIFIED);
		user.setOtpNumber(0L);
		usersRepository.save(user);

		return ResponseEntity.ok("Otp Verified !");

	}

	public ResponseEntity<String> updateForgotPassword(int userId, String newPassword) {
		Users user = usersRepository.findByUserId(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.badRequest().body("User not found!");
		}

		if (!user.getOtpStatus().equals("VERIFIED")) {
			return ResponseEntity.badRequest().body("OTP not verified");
		}

		user.setOtpStatus(OtpStatus.PENDING);
		user.setPassword(passwordEncoder.encode(newPassword));
		usersRepository.save(user);

		return ResponseEntity.ok("Successfully updated new password");

	}

	@Override
	public ResponseEntity<String> updateUserProfile(int userId, Users users) {
		Users existingUser = usersRepository.findByUserId(userId).orElse(null);

		if (existingUser == null) {
			return ResponseEntity.badRequest().body("User not found!");
		}

		existingUser.setFirstName(users.getFirstName());
		existingUser.setLastName(users.getLastName());
		existingUser.setPhoneNumber(users.getPhoneNumber());
		existingUser.setEmail(users.getEmail());
		existingUser.setDoorNumber(users.getDoorNumber());
		existingUser.setStreet(users.getStreet());
		existingUser.setVillage(users.getVillage());
		existingUser.setLandMark(users.getLandMark());
		existingUser.setCity(users.getCity());
		existingUser.setDistrict(users.getDistrict());
		existingUser.setPostalCode(users.getPostalCode());
		existingUser.setState(users.getState());
		existingUser.setCountry(users.getCountry());
		existingUser.setUserName(users.getUserName());
		usersRepository.save(existingUser);

		return ResponseEntity.ok("Profile updated successfully");
	}

	@Override
	public ResponseEntity<String> updatePassword(int userId, UpdatePasswordDetails updatePasswordDetails) {
		Users user = usersRepository.findByUserId(userId).orElse(null);
		if (user == null) {
			return ResponseEntity.badRequest().body("User not found!");
		}

		if (updatePasswordDetails.getOldPassword() == null || updatePasswordDetails.getNewPassword() == null) {
			return ResponseEntity.badRequest().body("Enter Valid Crendentails !");
		}

		if (!passwordEncoder.matches(updatePasswordDetails.getOldPassword(), user.getPassword())) {
			return ResponseEntity.badRequest().body("Invalid Crendentails !");
		}

		user.setPassword(passwordEncoder.encode(updatePasswordDetails.getNewPassword()));
		usersRepository.save(user);

		return ResponseEntity.ok("Password Updated Successfully");
	}

}
