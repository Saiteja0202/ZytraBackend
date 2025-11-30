package com.ecommerce.dtos;

import java.time.LocalDate;

import com.ecommerce.enums.MemberShipStatus;
import com.ecommerce.enums.Roles;

public class UsersRegistrationHistory {
	
	private int userId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String address;
	
	private String phoneNumber;
	
	private LocalDate registeredAt;
	
	private MemberShipStatus memberShipStatus;
	
	private Roles role;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDate registeredAt) {
		this.registeredAt = registeredAt;
	}

	public MemberShipStatus getMemberShipStatus() {
		return memberShipStatus;
	}

	public void setMemberShipStatus(MemberShipStatus memberShipStatus) {
		this.memberShipStatus = memberShipStatus;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public UsersRegistrationHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsersRegistrationHistory(int userId, String firstName, String lastName, String email, String address,
			String phoneNumber, LocalDate registeredAt, MemberShipStatus memberShipStatus, Roles role) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.registeredAt = registeredAt;
		this.memberShipStatus = memberShipStatus;
		this.role = role;
	}
	
	

}
